//,temp,VmwareStorageProcessor.java,1664,1709,temp,VmwareStorageManagerImpl.java,937,983
//,3
public class xxx {
    private void exportVolumeToSecondaryStorage(VirtualMachineMO vmMo, String volumePath, String secStorageUrl, String secStorageDir, String exportName, String workerVmName,
                                                Integer nfsVersion, boolean clonedWorkerVMNeeded) throws Exception {

        String secondaryMountPoint = _mountService.getMountPoint(secStorageUrl, nfsVersion);
        String exportPath = secondaryMountPoint + "/" + secStorageDir + "/" + exportName;

        synchronized (exportPath.intern()) {
            if (!new File(exportPath).exists()) {
                Script command = new Script(false, "mkdir", _timeout, s_logger);
                command.add("-p");
                command.add(exportPath);
                if (command.execute() != null) {
                    throw new Exception("unable to prepare snapshot backup directory");
                }
            }
        }

        VirtualMachineMO clonedVm = null;
        try {

            Pair<VirtualDisk, String> volumeDeviceInfo = vmMo.getDiskDevice(volumePath);
            if (volumeDeviceInfo == null) {
                String msg = "Unable to find related disk device for volume. volume path: " + volumePath;
                s_logger.error(msg);
                throw new Exception(msg);
            }

            if (clonedWorkerVMNeeded) {
                // 4 MB is the minimum requirement for VM memory in VMware
                vmMo.cloneFromCurrentSnapshot(workerVmName, 0, 4, volumeDeviceInfo.second(), VmwareHelper.getDiskDeviceDatastore(volumeDeviceInfo.first()));
                clonedVm = vmMo.getRunningHost().findVmOnHyperHost(workerVmName);
                if (clonedVm == null) {
                    String msg = "Unable to create dummy VM to export volume. volume path: " + volumePath;
                    s_logger.error(msg);
                    throw new Exception(msg);
                }
                clonedVm.exportVm(exportPath, exportName, false, false);  //Note: volss: not to create ova.
            } else {
                vmMo.exportVm(exportPath, exportName, false, false);
            }
        } finally {
            if (clonedVm != null) {
                clonedVm.detachAllDisks();
                clonedVm.destroy();
            }
        }
    }

};