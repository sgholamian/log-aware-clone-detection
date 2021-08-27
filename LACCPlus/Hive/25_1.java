//,temp,DbNotificationListener.java,959,967,temp,TxnHandler.java,4311,4317
//,3
public class xxx {
  private static void closeStmt(Statement stmt) {
    try {
      if (stmt != null && !stmt.isClosed()) {
        stmt.close();
      }
    } catch (SQLException e) {
      LOG.warn("Failed to close statement " + e.getMessage());
    }
  }

};