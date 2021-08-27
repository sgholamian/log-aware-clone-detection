//,temp,RenameTableHandler.java,34,83,temp,RenamePartitionHandler.java,38,75
//,3
public class xxx {
  @Override
  public List<Task<?>> handle(Context context)
      throws SemanticException {

    AlterPartitionMessage msg = deserializer.getAlterPartitionMessage(context.dmd.getPayload());
    String actualDbName = context.isDbNameEmpty() ? msg.getDB() : context.dbName;
    String actualTblName = msg.getTable();

    Map<String, String> newPartSpec = new LinkedHashMap<>();
    Map<String, String> oldPartSpec = new LinkedHashMap<>();
    TableName tableName = TableName.fromString(actualTblName, null, actualDbName);
    Table tableObj;
    ReplicationSpec replicationSpec = context.eventOnlyReplicationSpec();
    try {
      Iterator<String> beforeIterator = msg.getPtnObjBefore().getValuesIterator();
      Iterator<String> afterIterator = msg.getPtnObjAfter().getValuesIterator();
      tableObj = msg.getTableObj();
      for (FieldSchema fs : tableObj.getPartitionKeys()) {
        oldPartSpec.put(fs.getName(), beforeIterator.next());
        newPartSpec.put(fs.getName(), afterIterator.next());
      }

      AlterTableRenamePartitionDesc renamePtnDesc = new AlterTableRenamePartitionDesc(
              tableName, oldPartSpec, newPartSpec, replicationSpec, null);
      renamePtnDesc.setWriteId(msg.getWriteId());
      Task<DDLWork> renamePtnTask = TaskFactory.get(
          new DDLWork(readEntitySet, writeEntitySet, renamePtnDesc, true,
                  context.getDumpDirectory(), context.getMetricCollector()), context.hiveConf);
      context.log.debug("Added rename ptn task : {}:{}->{}",
                        renamePtnTask.getId(), oldPartSpec, newPartSpec);
      updatedMetadata.set(context.dmd.getEventTo().toString(), actualDbName, actualTblName, newPartSpec);
      return ReplUtils.addChildTask(renamePtnTask);
    } catch (Exception e) {
      throw (e instanceof SemanticException)
              ? (SemanticException) e
              : new SemanticException("Error reading message members", e);
    }
  }

};