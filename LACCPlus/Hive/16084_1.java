//,temp,GetTablesOperation.java,102,146,temp,GetFunctionsOperation.java,86,142
//,3
public class xxx {
  @Override
  public void runInternal() throws HiveSQLException {
    setState(OperationState.RUNNING);
    LOG.info("Fetching table metadata");
    try {
      IMetaStoreClient metastoreClient = getParentSession().getMetaStoreClient();
      String schemaPattern = convertSchemaPattern(schemaName);
      List<String> matchingDbs = metastoreClient.getDatabases(schemaPattern);
      if(isAuthV2Enabled()){
        List<HivePrivilegeObject> privObjs = HivePrivilegeObjectUtils.getHivePrivDbObjects(matchingDbs);
        String cmdStr = "catalog : " + catalogName + ", schemaPattern : " + schemaName;
        authorizeMetaGets(HiveOperationType.GET_TABLES, privObjs, cmdStr);
      }

      String tablePattern = convertIdentifierPattern(tableName, true);
      for (String dbName : metastoreClient.getDatabases(schemaPattern)) {
        for (TableMeta tableMeta :
                metastoreClient.getTableMeta(dbName, tablePattern, tableTypeList)) {
          String tableType = tableTypeMapping.mapToClientType(tableMeta.getTableType());
          rowSet.addRow(new Object[]{
                  DEFAULT_HIVE_CATALOG,
                  tableMeta.getDbName(),
                  tableMeta.getTableName(),
                  tableType,
                  tableMeta.getComments(),
                  null, null, null, null, null
          });

          if (LOG.isDebugEnabled()) {
            String debugMessage = getDebugMessage("table", RESULT_SET_SCHEMA);
            LOG.debug(debugMessage, DEFAULT_HIVE_CATALOG, tableMeta.getDbName(),
                    tableMeta.getTableName(), tableType, tableMeta.getComments());
          }
        }
        if (LOG.isDebugEnabled() && rowSet.numRows() == 0) {
          LOG.debug("No table metadata has been returned.");
        }
      }
      setState(OperationState.FINISHED);
      LOG.info("Fetching table metadata has been successfully finished");
    } catch (Exception e) {
      setState(OperationState.ERROR);
      throw new HiveSQLException(e);
    }
  }

};