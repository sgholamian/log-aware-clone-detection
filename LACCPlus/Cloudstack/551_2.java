//,temp,VmwareStorageProcessor.java,970,1021,temp,VmwareStorageManagerImpl.java,985,1040
//,3
public class xxx {
    private Pair<String, String> copyVolumeToSecStorage(VmwareHostService hostService, VmwareHypervisorHost hyperHost, CopyVolumeCommand cmd, String vmName, long volumeId,
            String poolId, String volumePath, String secStorageUrl, String workerVmName, Integer nfsVersion) throws Exception {

        String volumeFolder = String.valueOf(volumeId) + "/";
        VirtualMachineMO workerVm = null;
        VirtualMachineMO vmMo = null;
        String exportName = UUID.randomUUID().toString();
        String searchExcludedFolders = cmd.getContextParam("searchexludefolders");


        try {
            ManagedObjectReference morDs = HypervisorHostHelper.findDatastoreWithBackwardsCompatibility(hyperHost, poolId);

            if (morDs == null) {
                String msg = "Unable to find volumes's storage pool for copy volume operation";
                s_logger.error(msg);
                throw new Exception(msg);
            }

            boolean clonedWorkerVMNeeded = true;
            vmMo = hyperHost.findVmOnHyperHost(vmName);
            if (vmMo == null) {
                // create a dummy worker vm for attaching the volume
                DatastoreMO dsMo = new DatastoreMO(hyperHost.getContext(), morDs);
                workerVm = HypervisorHostHelper.createWorkerVM(hyperHost, dsMo, workerVmName);

                if (workerVm == null) {
                    String msg = "Unable to create worker VM to execute CopyVolumeCommand";
                    s_logger.error(msg);
                    throw new Exception(msg);
                }

                //attach volume to worker VM
                String datastoreVolumePath = getVolumePathInDatastore(dsMo, volumePath + ".vmdk", searchExcludedFolders);
                workerVm.attachDisk(new String[] {datastoreVolumePath}, morDs);
                vmMo = workerVm;
                clonedWorkerVMNeeded = false;
            } else {
                vmMo.createSnapshot(exportName, "Temporary snapshot for copy-volume command", false, false);
            }

            exportVolumeToSecondaryStorage(vmMo, volumePath, secStorageUrl, "volumes/" + volumeFolder, exportName, hostService.getWorkerName(hyperHost.getContext(), cmd, 1),
                    nfsVersion, clonedWorkerVMNeeded);
            return new Pair<String, String>(volumeFolder, exportName);

        } finally {
            if (vmMo != null && vmMo.getSnapshotMor(exportName) != null) {
                vmMo.removeSnapshot(exportName, false);
            }
            if (workerVm != null) {
                //detach volume and destroy worker vm
                workerVm.detachAllDisks();
                workerVm.destroy();
            }
        }
    }

};