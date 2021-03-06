//,temp,VmwareManagerImpl.java,776,794,temp,HypervManagerImpl.java,332,356
//,3
public class xxx {
    private void startupCleanup(String parent) {
        s_logger.info("Cleanup mounted mount points used in previous session");

        long mshostId = ManagementServerNode.getManagementServerId();

        // cleanup left-over NFS mounts from previous session
        String[] mounts = _storage.listFiles(parent + File.separator + String.valueOf(mshostId) + ".*");
        if (mounts != null && mounts.length > 0) {
            for (String mountPoint : mounts) {
                s_logger.info("umount NFS mount from previous session: " + mountPoint);

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