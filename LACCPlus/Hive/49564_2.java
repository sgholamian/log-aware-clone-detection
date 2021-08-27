//,temp,CompactionTxnHandler.java,681,717,temp,CompactionTxnHandler.java,497,551
//,3
public class xxx {
  @Override
  @RetrySemantics.SafeToRetry
  public void cleanTxnToWriteIdTable() throws MetaException {
    try {
      Connection dbConn = null;
      Statement stmt = null;
      ResultSet rs = null;

      try {
        // We query for minimum values in all the queries and they can only increase by any concurrent
        // operations. So, READ COMMITTED is sufficient.
        dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
        stmt = dbConn.createStatement();

        // First need to find the min_uncommitted_txnid which is currently seen by any open transactions.
        // If there are no txns which are currently open or aborted in the system, then current value of
        // max(TXNS.txn_id) could be min_uncommitted_txnid.
        String s = "SELECT MIN(\"RES\".\"ID\") AS \"ID\" FROM (" +
            "SELECT MAX(\"TXN_ID\") + 1 AS \"ID\" FROM \"TXNS\" " +
            "UNION " +
            "SELECT MIN(\"WS_COMMIT_ID\") AS \"ID\" FROM \"WRITE_SET\" " +
            "UNION " +
            "SELECT MIN(\"TXN_ID\") AS \"ID\" FROM \"TXNS\" WHERE \"TXN_STATE\" = " + TxnStatus.ABORTED +
            " OR \"TXN_STATE\" = " + TxnStatus.OPEN +
            ") \"RES\"";
        LOG.debug("Going to execute query <" + s + ">");
        rs = stmt.executeQuery(s);
        if (!rs.next()) {
          throw new MetaException("Transaction tables not properly initialized, no record found in TXNS");
        }
        long minUncommitedTxnid = rs.getLong(1);

        // As all txns below min_uncommitted_txnid are either committed or empty_aborted, we are allowed
        // to cleanup the entries less than min_uncommitted_txnid from the TXN_TO_WRITE_ID table.
        s = "DELETE FROM \"TXN_TO_WRITE_ID\" WHERE \"T2W_TXNID\" < " + minUncommitedTxnid;
        LOG.debug("Going to execute delete <" + s + ">");
        int rc = stmt.executeUpdate(s);
        LOG.info("Removed " + rc + " rows from TXN_TO_WRITE_ID with Txn Low-Water-Mark: " + minUncommitedTxnid);

        LOG.debug("Going to commit");
        dbConn.commit();
      } catch (SQLException e) {
        LOG.error("Unable to delete from txns table " + e.getMessage());
        LOG.debug("Going to rollback");
        rollbackDBConn(dbConn);
        checkRetryable(dbConn, e, "cleanTxnToWriteIdTable");
        throw new MetaException("Unable to connect to transaction database " +
                StringUtils.stringifyException(e));
      } finally {
        close(rs, stmt, dbConn);
      }
    } catch (RetryException e) {
      cleanTxnToWriteIdTable();
    }
  }

};