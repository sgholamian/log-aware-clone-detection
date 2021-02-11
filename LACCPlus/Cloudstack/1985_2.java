//,temp,VmwareManagerImpl.java,607,654,temp,HypervManagerImpl.java,152,194
//,3
public class xxx {
    private void prepareSecondaryStorageStore(String storageUrl) {
        String mountPoint = getMountPoint(storageUrl);

        GlobalLock lock = GlobalLock.getInternLock("prepare.systemvm");
        try {
            if (lock.lock(3600)) {
                try {
                    File patchFolder = new File(mountPoint + "/systemvm");
                    if (!patchFolder.exists()) {
                        if (!patchFolder.mkdirs()) {
                            String msg = "Unable to create systemvm folder on secondary storage. location: " + patchFolder.toString();
                            s_logger.error(msg);
                            throw new CloudRuntimeException(msg);
                        }
                    }

                    File srcIso = getSystemVMPatchIsoFile();
                    File destIso = new File(mountPoint + "/systemvm/" + getSystemVMIsoFileNameOnDatastore());
                    if (!destIso.exists()) {
                        s_logger.info("Copy System VM patch ISO file to secondary storage. source ISO: " +
                            srcIso.getAbsolutePath() + ", destination: " + destIso.getAbsolutePath());
                        try {
                            FileUtil.copyfile(srcIso, destIso);
                        } catch (IOException e) {
                            s_logger.error("Unexpected exception ", e);

                            String msg = "Unable to copy systemvm ISO on secondary storage. src location: " + srcIso.toString() + ", dest location: " + destIso;
                            s_logger.error(msg);
                            throw new CloudRuntimeException(msg);
                        }
                    } else {
                        if (s_logger.isTraceEnabled()) {
                            s_logger.trace("SystemVM ISO file " + destIso.getPath() + " already exists");
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
        } finally {
            lock.releaseRef();
        }
    }

};