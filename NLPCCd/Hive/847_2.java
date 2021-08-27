//,temp,sample_2802.java,2,11,temp,sample_4020.java,2,11
//,3
public class xxx {
public List<Task<? extends Serializable>> handle(Context context) throws SemanticException {
AlterTableMessage msg = deserializer.getAlterTableMessage(context.dmd.getPayload());
String actualDbName = context.isDbNameEmpty() ? msg.getDB() : context.dbName;
String actualTblName = context.isTableNameEmpty() ? msg.getTable() : context.tableName;
TruncateTableDesc truncateTableDesc = new TruncateTableDesc( actualDbName + "." + actualTblName, null, context.eventOnlyReplicationSpec());
Task<DDLWork> truncateTableTask = TaskFactory.get( new DDLWork(readEntitySet, writeEntitySet, truncateTableDesc), context.hiveConf);


log.info("added truncate tbl task");
}

};