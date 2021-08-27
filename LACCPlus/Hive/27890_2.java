//,temp,TruncatePartitionHandler.java,36,74,temp,TruncateTableHandler.java,32,53
//,3
public class xxx {
  @Override
  public List<Task<?>> handle(Context context) throws SemanticException {
    AlterTableMessage msg = deserializer.getAlterTableMessage(context.dmd.getPayload());
    final TableName tName = TableName.fromString(msg.getTable(), null,
        context.isDbNameEmpty() ? msg.getDB() : context.dbName);

    TruncateTableDesc truncateTableDesc = new TruncateTableDesc(tName, null, context.eventOnlyReplicationSpec());
    truncateTableDesc.setWriteId(msg.getWriteId());
    Task<DDLWork> truncateTableTask = TaskFactory.get(new DDLWork(readEntitySet, writeEntitySet,
                truncateTableDesc, true, context.getDumpDirectory(),
                context.getMetricCollector()), context.hiveConf);

    context.log.debug("Added truncate tbl task : {}:{}:{}", truncateTableTask.getId(),
        truncateTableDesc.getTableName(), truncateTableDesc.getWriteId());
    updatedMetadata.set(context.dmd.getEventTo().toString(), tName.getDb(), tName.getTable(), null);

    try {
      return ReplUtils.addChildTask(truncateTableTask);
    } catch (Exception e) {
      throw new SemanticException(e.getMessage());
    }
  }

};