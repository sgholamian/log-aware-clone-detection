//,temp,TxnHandler.java,4260,4279,temp,TxnHandler.java,2484,2504
//,3
public class xxx {
  @VisibleForTesting
  public int numLocksInLockTable() throws SQLException, MetaException {
    Connection dbConn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
      stmt = dbConn.createStatement();
      String s = "SELECT COUNT(*) FROM \"HIVE_LOCKS\"";
      LOG.debug("Going to execute query <" + s + ">");
      rs = stmt.executeQuery(s);
      rs.next();
      int rc = rs.getInt(1);
      // Necessary to clean up the transaction in the db.
      dbConn.rollback();
      return rc;
    } finally {
      close(rs, stmt, dbConn);
    }
  }

};