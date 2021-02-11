//,temp,Upgrade2214to30.java,565,577,temp,Upgrade225to226.java,84,107
//,3
public class xxx {
    private void dropKeysIfExist(Connection conn) {
        HashMap<String, List<String>> foreignKeys = new HashMap<String, List<String>>();
        HashMap<String, List<String>> indexes = new HashMap<String, List<String>>();

        // domain router table
        List<String> keys = new ArrayList<String>();
        keys.add("fk_domain_router__account_id");
        foreignKeys.put("domain_router", keys);

        keys = new ArrayList<String>();
        keys.add("i_domain_router__account_id");
        indexes.put("domain_router", keys);

        // drop all foreign keys first
        s_logger.debug("Dropping keys that don't exist in 2.2.6 version of the DB...");
        for (String tableName : foreignKeys.keySet()) {
            DbUpgradeUtils.dropKeysIfExist(conn, tableName, foreignKeys.get(tableName), true);
        }

        // drop indexes now
        for (String tableName : indexes.keySet()) {
            DbUpgradeUtils.dropKeysIfExist(conn, tableName, indexes.get(tableName), false);
        }
    }

};