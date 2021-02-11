//,temp,IscsiAdmStorageAdaptor.java,278,324,temp,IscsiAdmStorageAdaptor.java,188,213
//,3
public class xxx {
    private boolean disconnectPhysicalDisk(String host, int port, String iqn, String lun) {
        // use iscsiadm to log out of the iSCSI target and un-discover it

        // ex. sudo iscsiadm -m node -T iqn.2012-03.com.test:volume1 -p 192.168.233.10:3260 --logout
        Script iScsiAdmCmd = new Script(true, "iscsiadm", 0, s_logger);

        iScsiAdmCmd.add("-m", "node");
        iScsiAdmCmd.add("-T", iqn);
        iScsiAdmCmd.add("-p", host + ":" + port);
        iScsiAdmCmd.add("--logout");

        String result = iScsiAdmCmd.execute();

        if (result != null) {
            s_logger.debug("Failed to log out of iSCSI target /" + iqn + "/" + lun + " : message = " + result);
            System.out.println("Failed to log out of iSCSI target /" + iqn + "/" + lun + " : message = " + result);

            return false;
        } else {
            s_logger.debug("Successfully logged out of iSCSI target /" + iqn + "/" + lun);
            System.out.println("Successfully logged out of iSCSI target /" + iqn + "/" + lun);
        }

        // ex. sudo iscsiadm -m node -T iqn.2012-03.com.test:volume1 -p 192.168.233.10:3260 -o delete
        iScsiAdmCmd = new Script(true, "iscsiadm", 0, s_logger);

        iScsiAdmCmd.add("-m", "node");
        iScsiAdmCmd.add("-T", iqn);
        iScsiAdmCmd.add("-p", host + ":" + port);
        iScsiAdmCmd.add("-o", "delete");

        result = iScsiAdmCmd.execute();

        if (result != null) {
            s_logger.debug("Failed to remove iSCSI target /" + iqn + "/" + lun + " : message = " + result);
            System.out.println("Failed to remove iSCSI target /" + iqn + "/" + lun + " : message = " + result);

            return false;
        } else {
            s_logger.debug("Removed iSCSI target /" + iqn + "/" + lun);
            System.out.println("Removed iSCSI target /" + iqn + "/" + lun);
        }

        waitForDiskToBecomeUnavailable(host, port, iqn, lun);

        return true;
    }

};