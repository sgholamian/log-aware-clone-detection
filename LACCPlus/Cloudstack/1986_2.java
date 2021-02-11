//,temp,VmwareManagerImpl.java,750,774,temp,HypervManagerImpl.java,358,377
//,3
public class xxx {
    private void shutdownCleanup() {
        s_logger.info("Cleanup mounted mount points used in current session");
        synchronized (_storageMounts) {
             for (String mountPoint : _storageMounts.values()) {
                s_logger.info("umount NFS mount: " + mountPoint);

                String result = null;
                Script command = new Script(true, "umount", _timeout, s_logger);
                command.add(mountPoint);
                result = command.execute();
                if (result != null) {
                    s_logger.warn("Unable to umount " + mountPoint + " due to " + result);
                }
                File file = new File(mountPoint);
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

};