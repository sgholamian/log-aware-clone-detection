//,temp,DbNotificationListener.java,959,967,temp,TxnHandler.java,4311,4317
//,3
public class xxx {
  static void rollbackDBConn(Connection dbConn) {
    try {
      if (dbConn != null && !dbConn.isClosed()) dbConn.rollback();
    } catch (SQLException e) {
      LOG.warn("Failed to rollback db connection " + getMessage(e));
    }
  }

};