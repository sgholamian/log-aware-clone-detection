//,temp,GetSchemasOperation.java,63,90,temp,GetPrimaryKeysOperation.java,87,122
//,3
public class xxx {
  @Override
  public void runInternal() throws HiveSQLException {
    setState(OperationState.RUNNING);
    LOG.info("Fetching schema metadata");
    if (isAuthV2Enabled()) {
      String cmdStr = "catalog : " + catalogName + ", schemaPattern : " + schemaName;
      authorizeMetaGets(HiveOperationType.GET_SCHEMAS, null, cmdStr);
    }
    try {
      IMetaStoreClient metastoreClient = getParentSession().getMetaStoreClient();
      String schemaPattern = convertSchemaPattern(schemaName);
      for (String dbName : metastoreClient.getDatabases(schemaPattern)) {
        rowSet.addRow(new Object[] {dbName, DEFAULT_HIVE_CATALOG});
        if (LOG.isDebugEnabled()) {
          String debugMessage = getDebugMessage("schema", RESULT_SET_SCHEMA);
          LOG.debug(debugMessage, dbName, DEFAULT_HIVE_CATALOG);
        }
      }
      if (LOG.isDebugEnabled() && rowSet.numRows() == 0) {
        LOG.debug("No schema metadata has been returned.");
      }
      setState(OperationState.FINISHED);
      LOG.info("Fetching schema metadata has been successfully finished");
    } catch (Exception e) {
      setState(OperationState.ERROR);
      throw new HiveSQLException(e);
    }
  }

};