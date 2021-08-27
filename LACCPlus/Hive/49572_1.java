//,temp,TxnHandler.java,4970,4990,temp,TxnHandler.java,4948,4967
//,3
public class xxx {
  private void heartbeatTxn(Connection dbConn, long txnid)
    throws NoSuchTxnException, TxnAbortedException, SQLException, MetaException {
    // If the txnid is 0, then there are no transactions in this heartbeat
    if (txnid == 0) {
      return;
    }
    try (Statement stmt = dbConn.createStatement()) {
      String s = "UPDATE \"TXNS\" SET \"TXN_LAST_HEARTBEAT\" = " + getEpochFn(dbProduct) +
          " WHERE \"TXN_ID\" = " + txnid + " AND \"TXN_STATE\" = " + TxnStatus.OPEN;
      LOG.debug("Going to execute update <" + s + ">");
      int rc = stmt.executeUpdate(s);
      if (rc < 1) {
        ensureValidTxn(dbConn, txnid, stmt); // This should now throw some useful exception.
        LOG.error("Can neither heartbeat txn (txnId={}) nor confirm it as invalid.", txnid);
        dbConn.rollback();
        throw new NoSuchTxnException("No such txn: " + txnid);
      }
      LOG.debug("Successfully heartbeated for txnId={}", txnid);
      dbConn.commit();
    }
  }

};