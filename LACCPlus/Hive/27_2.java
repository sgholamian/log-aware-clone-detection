//,temp,DbNotificationListener.java,973,981,temp,TxnHandler.java,4318,4326
//,3
public class xxx {
  protected static void closeDbConn(Connection dbConn) {
    try {
      if (dbConn != null && !dbConn.isClosed()) {
        dbConn.close();
      }
    } catch (SQLException e) {
      LOG.warn("Failed to close db connection " + getMessage(e));
    }
  }

};