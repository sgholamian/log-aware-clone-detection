//,temp,VmwareStorageProcessor.java,3377,3440,temp,VmwareStorageManagerImpl.java,869,927
//,3
public class xxx {
    private Long restoreVolumeFromSecStorage(VmwareHypervisorHost hyperHost, DatastoreMO primaryDsMo, String newVolumeName, String secStorageUrl, String secStorageDir,
                                             String backupName, long wait, Integer nfsVersion) throws Exception {

        String secondaryMountPoint = mountService.getMountPoint(secStorageUrl, null);
        String srcOVAFileName;
        String srcOVFFileName;

        srcOVAFileName = secondaryMountPoint + "/" + secStorageDir + "/" + backupName + "." + ImageFormat.OVA.getFileExtension();
        srcOVFFileName = secondaryMountPoint + "/" + secStorageDir + "/" + backupName + ".ovf";

        String snapshotDir = "";
        if (backupName.contains("/")) {
            snapshotDir = backupName.split("/")[0];
        }

        File ovafile = new File(srcOVAFileName);

        File ovfFile = new File(srcOVFFileName);
        // String srcFileName = getOVFFilePath(srcOVAFileName);
        if (!ovfFile.exists()) {
            srcOVFFileName = getOVFFilePath(srcOVAFileName);
            if (srcOVFFileName == null && ovafile.exists()) {  // volss: ova file exists; o/w can't do tar
                Script command = new Script("tar", wait, s_logger);
                command.add("--no-same-owner");
                command.add("-xf", srcOVAFileName);
                command.setWorkDir(secondaryMountPoint + "/" + secStorageDir + "/" + snapshotDir);
                s_logger.info("Executing command: " + command.toString());
                String result = command.execute();
                if (result != null) {
                    String msg = "Unable to unpack snapshot OVA file at: " + srcOVAFileName;
                    s_logger.error(msg);
                    throw new Exception(msg);
                }
                srcOVFFileName = getOVFFilePath(srcOVAFileName);
            } else if (srcOVFFileName == null) {
                String msg = "Unable to find snapshot OVA file at: " + srcOVAFileName;
                s_logger.error(msg);
                throw new Exception(msg);
            }
        }
        if (srcOVFFileName == null) {
            String msg = "Unable to locate OVF file in template package directory: " + srcOVAFileName;
            s_logger.error(msg);
            throw new Exception(msg);
        }

        VirtualMachineMO clonedVm = null;
        try {
            hyperHost.importVmFromOVF(srcOVFFileName, newVolumeName, primaryDsMo, "thin");
            clonedVm = hyperHost.findVmOnHyperHost(newVolumeName);
            if (clonedVm == null) {
                throw new Exception("Unable to create container VM for volume creation");
            }

            clonedVm.moveAllVmDiskFiles(primaryDsMo, "", false);
            clonedVm.detachAllDisks();
            return _storage.getSize(srcOVFFileName);
        } finally {
            if (clonedVm != null) {
                clonedVm.detachAllDisks();
                clonedVm.destroy();
            }
        }
    }

};