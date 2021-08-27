//,temp,RenameTableHandler.java,34,83,temp,RenamePartitionHandler.java,38,75
//,3
public class xxx {
  @Override
  public List<Task<?>> handle(Context context)
      throws SemanticException {
    AlterTableMessage msg = deserializer.getAlterTableMessage(context.dmd.getPayload());
    try {
      Table tableObjBefore = msg.getTableObjBefore();
      Table tableObjAfter = msg.getTableObjAfter();
      String oldDbName = tableObjBefore.getDbName();
      String newDbName = tableObjAfter.getDbName();

      if (!context.isDbNameEmpty()) {
        // If we're loading into a db, instead of into the warehouse, then the oldDbName and
        // newDbName must be the same
        if (!oldDbName.equalsIgnoreCase(newDbName)) {
          throw new SemanticException("Cannot replicate an event renaming a table across"
              + " databases into a db level load " + oldDbName + "->" + newDbName);
        } else {
          // both were the same, and can be replaced by the new db we're loading into.
          oldDbName = context.dbName;
          newDbName = context.dbName;
        }
      }

      TableName oldName = TableName.fromString(tableObjBefore.getTableName(), null, oldDbName);
      TableName newName = TableName.fromString(tableObjAfter.getTableName(), null, newDbName);
      ReplicationSpec replicationSpec = context.eventOnlyReplicationSpec();

      AlterTableRenameDesc renameTableDesc =
          new AlterTableRenameDesc(oldName, replicationSpec, false, newName.getNotEmptyDbTable());
      renameTableDesc.setWriteId(msg.getWriteId());
      Task<DDLWork> renameTableTask = TaskFactory.get(new DDLWork(readEntitySet, writeEntitySet,
              renameTableDesc, true, context.getDumpDirectory(),
              context.getMetricCollector()), context.hiveConf);
      context.log.debug("Added rename table task : {}:{}->{}",
                        renameTableTask.getId(), oldName.getNotEmptyDbTable(), newName.getNotEmptyDbTable());

      // oldDbName and newDbName *will* be the same if we're here
      updatedMetadata.set(context.dmd.getEventTo().toString(), newDbName,
              tableObjAfter.getTableName(), null);

      // Note : edge-case here in interaction with table-level REPL LOAD, where that nukes out
      // tablesUpdated. However, we explicitly don't support repl of that sort, and error out above
      // if so. If that should ever change, this will need reworking.
      return ReplUtils.addChildTask(renameTableTask);
    } catch (Exception e) {
      throw (e instanceof SemanticException)
          ? (SemanticException) e
          : new SemanticException("Error reading message members", e);
    }
  }

};