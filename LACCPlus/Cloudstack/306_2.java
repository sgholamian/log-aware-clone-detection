//,temp,CitrixResourceBase.java,5174,5190,temp,CitrixResourceBase.java,1100,1120
//,3
public class xxx {
    String createTemplateFromSnapshot(final Connection conn, final String templatePath, final String snapshotPath, final int wait) {
        final String tmpltLocalDir = UUID.randomUUID().toString();
        final String results = callHostPluginAsync(conn, "vmopspremium", "create_privatetemplate_from_snapshot", wait, "templatePath", templatePath, "snapshotPath", snapshotPath, "tmpltLocalDir",
                tmpltLocalDir);
        String errMsg = null;
        if (results == null || results.isEmpty()) {
            errMsg = "create_privatetemplate_from_snapshot return null";
        } else {
            final String[] tmp = results.split("#");
            final String status = tmp[0];
            if (status.equals("0")) {
                return results;
            } else {
                errMsg = "create_privatetemplate_from_snapshot failed due to " + tmp[1];
            }
        }
        final String source = "cloud_mount/" + tmpltLocalDir;
        killCopyProcess(conn, source);
        s_logger.warn(errMsg);
        throw new CloudRuntimeException(errMsg);
    }

};