//,temp,CompactionTxnHandler.java,681,717,temp,CompactionTxnHandler.java,497,551
//,3
public class xxx {
  @Override
  @RetrySemantics.Idempotent
  public void revokeTimedoutWorkers(long timeout) throws MetaException {
    try {
      Connection dbConn = null;
      Statement stmt = null;
      try {
        dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
        long latestValidStart = getDbTime(dbConn) - timeout;
        stmt = dbConn.createStatement();
        String s = "UPDATE \"COMPACTION_QUEUE\" SET \"CQ_WORKER_ID\" = NULL, \"CQ_START\" = NULL, \"CQ_STATE\" = '"
          + INITIATED_STATE+ "' WHERE \"CQ_STATE\" = '" + WORKING_STATE + "' AND \"CQ_START\" < "
          +  latestValidStart;
        LOG.debug("Going to execute update <" + s + ">");
        // It isn't an error if the following returns no rows, as the local workers could have died
        // with  nothing assigned to them.
        int updated = stmt.executeUpdate(s);
        LOG.info(updated + " compaction queue entries timed out, set back to " + INITIATED_RESPONSE + " state. Latest "
            + "valid start: " + latestValidStart);
        LOG.debug("Going to commit");
        dbConn.commit();
      } catch (SQLException e) {
        LOG.error("Unable to change dead worker's records back to initiated state " +
          e.getMessage());
        LOG.debug("Going to rollback");
        rollbackDBConn(dbConn);
        checkRetryable(dbConn, e, "revokeTimedoutWorkers(timeout:" + timeout + ")");
        throw new MetaException("Unable to connect to transaction database " +
          StringUtils.stringifyException(e));
      } finally {
        closeStmt(stmt);
        closeDbConn(dbConn);
      }
    } catch (RetryException e) {
      revokeTimedoutWorkers(timeout);
    }
  }

};