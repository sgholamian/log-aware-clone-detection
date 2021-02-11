//,temp,Upgrade442to450.java,164,175,temp,Upgrade301to302.java,62,73
//,3
public class xxx {
    private void dropDuplicatedForeignKeyFromAsyncJobTable(Connection conn) {
        HashMap<String, List<String>> foreignKeys = new HashMap<String, List<String>>();
        List<String> keys = new ArrayList<String>();

        keys.add("fk_async_job_join_map__join_job_id");
        foreignKeys.put("async_job_join_map", keys);

        s_logger.debug("Dropping fk_async_job_join_map__join_job_id key from async_job_join_map table");
        for (Map.Entry<String, List<String>> entry: foreignKeys.entrySet()) {
            DbUpgradeUtils.dropKeysIfExist(conn,entry.getKey(), entry.getValue(), true);
        }
    }

};