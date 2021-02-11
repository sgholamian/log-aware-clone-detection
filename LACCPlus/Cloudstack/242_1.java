//,temp,Upgrade2214to30.java,565,577,temp,Upgrade301to302.java,62,73
//,3
public class xxx {
    private void dropKeysIfExist(Connection conn) {
        HashMap<String, List<String>> uniqueKeys = new HashMap<String, List<String>>();
        List<String> keys = new ArrayList<String>();
        keys.add("public_ip_address");
        uniqueKeys.put("console_proxy", keys);
        uniqueKeys.put("secondary_storage_vm", keys);

        // drop keys
        s_logger.debug("Dropping public_ip_address keys from `cloud`.`secondary_storage_vm` and console_proxy tables...");
        for (String tableName : uniqueKeys.keySet()) {
            DbUpgradeUtils.dropKeysIfExist(conn, tableName, uniqueKeys.get(tableName), false);
        }
    }

};