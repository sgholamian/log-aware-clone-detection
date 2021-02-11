//,temp,IscsiAdmStorageAdaptor.java,278,324,temp,IscsiAdmStorageAdaptor.java,188,213
//,3
public class xxx {
    private void executeChapCommand(String path, KVMStoragePool pool, String nParameter, String vParameter, String detail) throws Exception {
        Script iScsiAdmCmd = new Script(true, "iscsiadm", 0, s_logger);

        iScsiAdmCmd.add("-m", "node");
        iScsiAdmCmd.add("-T", getIqn(path));
        iScsiAdmCmd.add("-p", pool.getSourceHost() + ":" + pool.getSourcePort());
        iScsiAdmCmd.add("--op", "update");
        iScsiAdmCmd.add("-n", nParameter);
        iScsiAdmCmd.add("-v", vParameter);

        String result = iScsiAdmCmd.execute();

        boolean useDetail = detail != null && detail.trim().length() > 0;

        detail = useDetail ? detail.trim() + " " : detail;

        if (result != null) {
            s_logger.debug("Failed to execute CHAP " + (useDetail ? detail : "") + "command for iSCSI target " + path + " : message = " + result);
            System.out.println("Failed to execute CHAP " + (useDetail ? detail : "") + "command for iSCSI target " + path + " : message = " + result);

            throw new Exception("Failed to execute CHAP " + (useDetail ? detail : "") + "command for iSCSI target " + path + " : message = " + result);
        } else {
            s_logger.debug("CHAP " + (useDetail ? detail : "") + "command executed successfully for iSCSI target " + path);
            System.out.println("CHAP " + (useDetail ? detail : "") + "command executed successfully for iSCSI target " + path);
        }
    }

};