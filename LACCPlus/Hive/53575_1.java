//,temp,TxnHandler.java,2611,2664,temp,TxnHandler.java,2565,2609
//,3
public class xxx {
  @Override
  public long cleanupMaterializationRebuildLocks(ValidTxnList validTxnList, long timeout) throws MetaException {
    try {
      // Aux values
      long cnt = 0L;
      List<Long> txnIds = new ArrayList<>();
      long timeoutTime = Instant.now().toEpochMilli() - timeout;

      Connection dbConn = null;
      Statement stmt = null;
      ResultSet rs = null;
      try {
        lockInternal();
        dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
        stmt = dbConn.createStatement();

        String selectQ = "SELECT \"MRL_TXN_ID\", \"MRL_LAST_HEARTBEAT\" FROM \"MATERIALIZATION_REBUILD_LOCKS\"";
        LOG.debug("Going to execute query <" + selectQ + ">");
        rs = stmt.executeQuery(selectQ);
        while(rs.next()) {
          long lastHeartbeat = rs.getLong(2);
          if (lastHeartbeat < timeoutTime) {
            // The heartbeat has timeout, double check whether we can remove it
            long txnId = rs.getLong(1);
            if (validTxnList.isTxnValid(txnId) || validTxnList.isTxnAborted(txnId)) {
              // Txn was committed (but notification was not received) or it was aborted.
              // Either case, we can clean it up
              txnIds.add(txnId);
            }
          }
        }
        if (!txnIds.isEmpty()) {
          String deleteQ = "DELETE FROM \"MATERIALIZATION_REBUILD_LOCKS\" WHERE" +
              " \"MRL_TXN_ID\" IN(" + StringUtils.join(",", txnIds) + ") ";
          LOG.debug("Going to execute update <" + deleteQ + ">");
          cnt = stmt.executeUpdate(deleteQ);
        }
        LOG.debug("Going to commit");
        dbConn.commit();
        return cnt;
      } catch (SQLException e) {
        LOG.debug("Going to rollback");
        rollbackDBConn(dbConn);
        checkRetryable(dbConn, e, "cleanupMaterializationRebuildLocks");
        throw new MetaException("Unable to clean rebuild locks due to " +
            StringUtils.stringifyException(e));
      } finally {
        close(rs, stmt, dbConn);
        unlockInternal();
      }
    } catch (RetryException e) {
      return cleanupMaterializationRebuildLocks(validTxnList, timeout);
    }
  }

};