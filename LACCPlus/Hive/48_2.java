//,temp,DummyTxnManager.java,316,326,temp,TxnHandler.java,4332,4338
//,3
public class xxx {
  protected static void closeStmt(Statement stmt) {
    try {
      if (stmt != null && !stmt.isClosed()) stmt.close();
    } catch (SQLException e) {
      LOG.warn("Failed to close statement " + getMessage(e));
    }
  }

};