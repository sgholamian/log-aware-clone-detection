//,temp,GetSchemasOperation.java,63,90,temp,GetPrimaryKeysOperation.java,87,122
//,3
public class xxx {
  @Override
  public void runInternal() throws HiveSQLException {
    setState(OperationState.RUNNING);
    LOG.info("Fetching primary key metadata");
    try {
      IMetaStoreClient metastoreClient = getParentSession().getMetaStoreClient();
      PrimaryKeysRequest sqlReq = new PrimaryKeysRequest(schemaName, tableName);
      List<SQLPrimaryKey> pks = metastoreClient.getPrimaryKeys(sqlReq);
      if (pks == null) {
        return;
      }
      for (SQLPrimaryKey pk : pks) {
        Object[] rowData = new Object[] {
            catalogName,
            pk.getTable_db(),
            pk.getTable_name(),
            pk.getColumn_name(),
            pk.getKey_seq(),
            pk.getPk_name()
        };
        rowSet.addRow(rowData);
        if (LOG.isDebugEnabled()) {
          String debugMessage = getDebugMessage("primary key", RESULT_SET_SCHEMA);
          LOG.debug(debugMessage, rowData);
        }
      }
      if (LOG.isDebugEnabled() && rowSet.numRows() == 0) {
        LOG.debug("No primary key metadata has been returned.");
      }
      setState(OperationState.FINISHED);
      LOG.info("Fetching primary key metadata has been successfully finished");
    } catch (Exception e) {
      setState(OperationState.ERROR);
      throw new HiveSQLException(e);
    }
  }

};