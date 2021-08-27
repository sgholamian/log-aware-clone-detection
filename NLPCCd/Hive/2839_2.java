//,temp,sample_716.java,2,13,temp,sample_2186.java,2,11
//,3
public class xxx {
public static boolean shouldReplicate(NotificationEvent tableForEvent, ReplicationSpec replicationSpec, Hive db, HiveConf hiveConf) {
Table table;
try {
table = db.getTable(tableForEvent.getDbName(), tableForEvent.getTableName());
} catch (HiveException e) {


log.info("error while getting table info for");
}
}

};