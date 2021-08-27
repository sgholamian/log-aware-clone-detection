//,temp,TxnHandler.java,427,456,temp,CompactionTxnHandler.java,1230,1271
//,3
public class xxx {
  @Override
  @RetrySemantics.Idempotent
  @Deprecated
  public long findMinTxnIdSeenOpen() throws MetaException {
    if (!useMinHistoryLevel) {
      return -1L;
    }
    Connection dbConn = null;
    try {
      try {
        dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
        long minOpenTxn;
        try (Statement stmt = dbConn.createStatement()) {
          try (ResultSet rs = stmt.executeQuery("SELECT MIN(\"MHL_MIN_OPEN_TXNID\") FROM \"MIN_HISTORY_LEVEL\"")) {
            if (!rs.next()) {
              throw new IllegalStateException("Scalar query returned no rows?!");
            }
            minOpenTxn = rs.getLong(1);
            if (rs.wasNull()) {
              minOpenTxn = -1L;
            }
          }
        }
        dbConn.rollback();
        return minOpenTxn;
      } catch (SQLException e) {
        if (dbProduct.isTableNotExistsError(e)) {
          useMinHistoryLevel = false;
          return -1L;
        } else {
          LOG.error("Unable to execute findMinTxnIdSeenOpen", e);
          rollbackDBConn(dbConn);
          checkRetryable(dbConn, e, "findMinTxnIdSeenOpen");
          throw new MetaException("Unable to execute findMinTxnIdSeenOpen() " + StringUtils.stringifyException(e));
        }
      } finally {
        closeDbConn(dbConn);
      }
    } catch (RetryException e) {
      return findMinTxnIdSeenOpen();
    }
  }

};