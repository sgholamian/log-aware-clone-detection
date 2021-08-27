//,temp,CompactionTxnHandler.java,635,670,temp,CompactionTxnHandler.java,248,283
//,3
public class xxx {
  @Override
  @RetrySemantics.SafeToRetry
  public void markCompacted(CompactionInfo info) throws MetaException {
    try {
      Connection dbConn = null;
      Statement stmt = null;
      try {
        dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
        stmt = dbConn.createStatement();
        String s = "UPDATE \"COMPACTION_QUEUE\" SET \"CQ_STATE\" = '" + READY_FOR_CLEANING + "', "
            + "\"CQ_WORKER_ID\" = NULL"
            + " WHERE \"CQ_ID\" = " + info.id;
        LOG.debug("Going to execute update <" + s + ">");
        int updCnt = stmt.executeUpdate(s);
        if (updCnt != 1) {
          LOG.error("Unable to set cq_state=" + READY_FOR_CLEANING + " for compaction record: " + info + ". updCnt=" + updCnt);
          LOG.debug("Going to rollback");
          dbConn.rollback();
        }
        LOG.debug("Going to commit");
        dbConn.commit();
      } catch (SQLException e) {
        LOG.error("Unable to update compaction queue " + e.getMessage());
        LOG.debug("Going to rollback");
        rollbackDBConn(dbConn);
        checkRetryable(dbConn, e, "markCompacted(" + info + ")");
        throw new MetaException("Unable to connect to transaction database " +
          StringUtils.stringifyException(e));
      } finally {
        closeStmt(stmt);
        closeDbConn(dbConn);
      }
    } catch (RetryException e) {
      markCompacted(info);
    }
  }

};