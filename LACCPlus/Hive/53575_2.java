//,temp,TxnHandler.java,2611,2664,temp,TxnHandler.java,2565,2609
//,3
public class xxx {
  @Override
  public boolean heartbeatLockMaterializationRebuild(String dbName, String tableName, long txnId)
      throws MetaException {
    try {
      Connection dbConn = null;
      PreparedStatement pst = null;
      try {
        lockInternal();
        dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
        String s = "UPDATE \"MATERIALIZATION_REBUILD_LOCKS\"" +
            " SET \"MRL_LAST_HEARTBEAT\" = " + Instant.now().toEpochMilli() +
            " WHERE \"MRL_TXN_ID\" = " + txnId +
            " AND \"MRL_DB_NAME\" = ?" +
            " AND \"MRL_TBL_NAME\" = ?";
        pst = sqlGenerator.prepareStmtWithParameters(dbConn, s, Arrays.asList(dbName, tableName));
        LOG.debug("Going to execute update <" + s.replaceAll("\\?", "{}") + ">",
                quoteString(dbName), quoteString(tableName));
        int rc = pst.executeUpdate();
        if (rc < 1) {
          LOG.debug("Going to rollback");
          dbConn.rollback();
          LOG.info("No lock found for rebuild of " + TableName.getDbTable(dbName, tableName) +
              " when trying to heartbeat");
          // It could not be renewed, return that information
          return false;
        }
        LOG.debug("Going to commit");
        dbConn.commit();
        // It could be renewed, return that information
        return true;
      } catch (SQLException e) {
        LOG.debug("Going to rollback");
        rollbackDBConn(dbConn);
        checkRetryable(dbConn, e,
            "heartbeatLockMaterializationRebuild(" + TableName.getDbTable(dbName, tableName) + ", " + txnId + ")");
        throw new MetaException("Unable to heartbeat rebuild lock due to " +
            StringUtils.stringifyException(e));
      } finally {
        close(null, pst, dbConn);
        unlockInternal();
      }
    } catch (RetryException e) {
      return heartbeatLockMaterializationRebuild(dbName, tableName ,txnId);
    }
  }

};