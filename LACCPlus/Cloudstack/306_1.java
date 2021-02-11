//,temp,CitrixResourceBase.java,5174,5190,temp,CitrixResourceBase.java,1100,1120
//,3
public class xxx {
    public String upgradeSnapshot(final Connection conn, final String templatePath, final String snapshotPath) {
        final String results = callHostPluginAsync(conn, "vmopspremium", "upgrade_snapshot", 2 * 60 * 60, "templatePath", templatePath, "snapshotPath", snapshotPath);

        if (results == null || results.isEmpty()) {
            final String msg = "upgrade_snapshot return null";
            s_logger.warn(msg);
            throw new CloudRuntimeException(msg);
        }
        final String[] tmp = results.split("#");
        final String status = tmp[0];
        if (status.equals("0")) {
            return results;
        } else {
            s_logger.warn(results);
            throw new CloudRuntimeException(results);
        }
    }

};