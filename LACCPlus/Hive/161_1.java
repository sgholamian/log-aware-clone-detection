//,temp,TxnHandler.java,873,889,temp,SchemaToolTaskMoveTable.java,111,120
//,3
public class xxx {
  private long getHighWaterMark(Statement stmt) throws SQLException, MetaException {
    String s = "SELECT MAX(\"TXN_ID\") FROM \"TXNS\"";
    LOG.debug("Going to execute query <" + s + ">");
    long maxOpenTxnId;
    try (ResultSet maxOpenTxnIdRs = stmt.executeQuery(s)) {
      maxOpenTxnIdRs.next();
      maxOpenTxnId = maxOpenTxnIdRs.getLong(1);
      if (maxOpenTxnIdRs.wasNull()) {
        /*
         * TXNS always contains at least one transaction,
         * the row where txnid = (select max(txnid) where txn_started < epoch - TXN_OPENTXN_TIMEOUT) is never deleted
         */
        throw new MetaException("Transaction tables not properly " + "initialized, null record found in MAX(TXN_ID)");
      }
    }
    return maxOpenTxnId;
  }

};