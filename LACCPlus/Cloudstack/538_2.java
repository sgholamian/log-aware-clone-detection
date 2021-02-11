//,temp,VmwareStorageManagerImpl.java,1390,1463,temp,VmwareStorageManagerImpl.java,1174,1241
//,3
public class xxx {
    @Override
    public CreateVMSnapshotAnswer execute(VmwareHostService hostService, CreateVMSnapshotCommand cmd) {
        List<VolumeObjectTO> volumeTOs = cmd.getVolumeTOs();
        String vmName = cmd.getVmName();
        String vmSnapshotName = cmd.getTarget().getSnapshotName();
        String vmSnapshotDesc = cmd.getTarget().getDescription();
        boolean snapshotMemory = cmd.getTarget().getType() == VMSnapshot.Type.DiskAndMemory;
        boolean quiescevm = cmd.getTarget().getQuiescevm();
        VirtualMachineMO vmMo = null;
        VmwareContext context = hostService.getServiceContext(cmd);

        try {
            VmwareHypervisorHost hyperHost = hostService.getHyperHost(context, cmd);

            // wait if there are already VM snapshot task running
            ManagedObjectReference taskmgr = context.getServiceContent().getTaskManager();
            List<ManagedObjectReference> tasks = context.getVimClient().getDynamicProperty(taskmgr, "recentTask");

            for (ManagedObjectReference taskMor : tasks) {
                TaskInfo info = (TaskInfo)(context.getVimClient().getDynamicProperty(taskMor, "info"));

                if (info.getEntityName().equals(cmd.getVmName()) && StringUtils.isNotBlank(info.getName()) && info.getName().equalsIgnoreCase("CreateSnapshot_Task")) {
                    if (!(info.getState().equals(TaskInfoState.SUCCESS) || info.getState().equals(TaskInfoState.ERROR))) {
                        s_logger.debug("There is already a VM snapshot task running, wait for it");
                        context.getVimClient().waitForTask(taskMor);
                    }
                }
            }

            vmMo = hyperHost.findVmOnHyperHost(vmName);

            if (vmMo == null) {
                vmMo = hyperHost.findVmOnPeerHyperHost(vmName);
            }

            if (vmMo == null) {
                String msg = "Unable to find VM for CreateVMSnapshotCommand";
                s_logger.info(msg);

                return new CreateVMSnapshotAnswer(cmd, false, msg);
            } else {
                if (vmMo.getSnapshotMor(vmSnapshotName) != null) {
                    s_logger.info("VM snapshot " + vmSnapshotName + " already exists");
                } else if (!vmMo.createSnapshot(vmSnapshotName, vmSnapshotDesc, snapshotMemory, quiescevm)) {
                    return new CreateVMSnapshotAnswer(cmd, false, "Unable to create snapshot due to esxi internal failed");
                }

                Map<String, String> mapNewDisk = getNewDiskMap(vmMo);

                setVolumeToPathAndSize(volumeTOs, mapNewDisk, context, hyperHost, cmd.getVmName());

                return new CreateVMSnapshotAnswer(cmd, cmd.getTarget(), volumeTOs);
            }
        } catch (Exception e) {
            String msg = e.getMessage();
            s_logger.error("failed to create snapshot for vm:" + vmName + " due to " + msg);

            try {
                if (vmMo.getSnapshotMor(vmSnapshotName) != null) {
                    vmMo.removeSnapshot(vmSnapshotName, false);
                }
            } catch (Exception e1) {
                s_logger.info("[ignored]" + "error during snapshot remove: " + e1.getLocalizedMessage());
            }

            return new CreateVMSnapshotAnswer(cmd, false, e.getMessage());
        }
    }

};