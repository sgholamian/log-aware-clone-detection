//,temp,VmwareManagerImpl.java,732,748,temp,HypervManagerImpl.java,271,287
//,2
public class xxx {
    private String setupMountPoint(String parent) {
        String mountPoint = null;
        long mshostId = ManagementServerNode.getManagementServerId();
        for (int i = 0; i < 10; i++) {
            String mntPt = parent + File.separator + String.valueOf(mshostId) + "." + Integer.toHexString(_rand.nextInt(Integer.MAX_VALUE));
            File file = new File(mntPt);
            if (!file.exists()) {
                if (_storage.mkdir(mntPt)) {
                    mountPoint = mntPt;
                    break;
                }
            }
            s_logger.error("Unable to create mount: " + mntPt);
        }

        return mountPoint;
    }

};