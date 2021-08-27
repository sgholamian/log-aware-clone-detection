//,temp,CompactionTxnHandler.java,635,670,temp,CompactionTxnHandler.java,248,283
//,3
public class xxx {
  @Override
  @RetrySemantics.Idempotent
  public void revokeFromLocalWorkers(String hostname) throws MetaException {
    try {
      Connection dbConn = null;
      Statement stmt = null;
      try {
        dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
        stmt = dbConn.createStatement();
        String s = "UPDATE \"COMPACTION_QUEUE\" SET \"CQ_WORKER_ID\" = NULL, \"CQ_START\" = NULL, \"CQ_STATE\" = '"
          + INITIATED_STATE+ "' WHERE \"CQ_STATE\" = '" + WORKING_STATE + "' AND \"CQ_WORKER_ID\" LIKE '"
          +  hostname + "%'";
        LOG.debug("Going to execute update <" + s + ">");
        // It isn't an error if the following returns no rows, as the local workers could have died
        // with  nothing assigned to them.
        int updated = stmt.executeUpdate(s);
        LOG.debug("Set " + updated + " compaction queue entries to " + INITIATED_RESPONSE + " state for host "
            + hostname);
        LOG.debug("Going to commit");
        dbConn.commit();
      } catch (SQLException e) {
        LOG.error("Unable to change dead worker's records back to initiated state " +
          e.getMessage());
        LOG.debug("Going to rollback");
        rollbackDBConn(dbConn);
        checkRetryable(dbConn, e, "revokeFromLocalWorkers(hostname:" + hostname +")");
        throw new MetaException("Unable to connect to transaction database " +
          StringUtils.stringifyException(e));
      } finally {
        closeStmt(stmt);
        closeDbConn(dbConn);
      }
    } catch (RetryException e) {
      revokeFromLocalWorkers(hostname);
    }
  }

};