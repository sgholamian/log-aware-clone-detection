//,temp,Upgrade225to226.java,69,82,temp,Upgrade301to302.java,62,73
//,3
public class xxx {
    private void dropKeysIfExists(Connection conn) {
        HashMap<String, List<String>> uniqueKeys = new HashMap<String, List<String>>();
        List<String> keys = new ArrayList<String>();

        keys.add("i_host__allocation_state");
        uniqueKeys.put("host", keys);

        s_logger.debug("Droping i_host__allocation_state key in host table");
        for (String tableName : uniqueKeys.keySet()) {
            DbUpgradeUtils.dropKeysIfExist(conn, tableName, uniqueKeys.get(tableName), false);
        }
    }

};