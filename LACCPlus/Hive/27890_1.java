//,temp,TruncatePartitionHandler.java,36,74,temp,TruncateTableHandler.java,32,53
//,3
public class xxx {
  @Override
  public List<Task<?>> handle(Context context) throws SemanticException {
    AlterPartitionMessage msg = deserializer.getAlterPartitionMessage(context.dmd.getPayload());
    final TableName tName = TableName.fromString(msg.getTable(), null,
        context.isDbNameEmpty() ? msg.getDB() : context.dbName);

    Map<String, String> partSpec = new LinkedHashMap<>();
    org.apache.hadoop.hive.metastore.api.Table tblObj;
    try {
      tblObj = msg.getTableObj();
      Iterator<String> afterIterator = msg.getPtnObjAfter().getValuesIterator();
      for (FieldSchema fs : tblObj.getPartitionKeys()) {
        partSpec.put(fs.getName(), afterIterator.next());
      }
    } catch (Exception e) {
      if (!(e instanceof SemanticException)) {
        throw new SemanticException("Error reading message members", e);
      } else {
        throw (SemanticException) e;
      }
    }

    TruncateTableDesc truncateTableDesc = new TruncateTableDesc(
            tName, partSpec,
            context.eventOnlyReplicationSpec());
    truncateTableDesc.setWriteId(msg.getWriteId());
    Task<DDLWork> truncatePtnTask = TaskFactory.get(
        new DDLWork(readEntitySet, writeEntitySet, truncateTableDesc, true,
                context.getDumpDirectory(), context.getMetricCollector()), context.hiveConf);
    context.log.debug("Added truncate ptn task : {}:{}:{}", truncatePtnTask.getId(),
        truncateTableDesc.getTableName(), truncateTableDesc.getWriteId());
    updatedMetadata.set(context.dmd.getEventTo().toString(), tName.getDb(), tName.getTable(), partSpec);

    try {
      return ReplUtils.addChildTask(truncatePtnTask);
    } catch (Exception e) {
      throw new SemanticException(e.getMessage());
    }
  }

};