//,temp,Upgrade2214to30.java,565,577,temp,Upgrade225to226.java,69,82
//,3
public class xxx {
    private void dropTableColumnsIfExist(Connection conn) {
        HashMap<String, List<String>> tablesToModify = new HashMap<String, List<String>>();

        // domain router table
        List<String> columns = new ArrayList<String>();
        columns.add("account_id");
        columns.add("domain_id");
        tablesToModify.put("domain_router", columns);

        s_logger.debug("Dropping columns that don't exist in 2.2.6 version of the DB...");
        for (String tableName : tablesToModify.keySet()) {
            DbUpgradeUtils.dropTableColumnsIfExist(conn, tableName, tablesToModify.get(tableName));
        }
    }

};