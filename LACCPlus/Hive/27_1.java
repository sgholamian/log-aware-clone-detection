//,temp,DbNotificationListener.java,973,981,temp,TxnHandler.java,4318,4326
//,3
public class xxx {
  private static void close(ResultSet rs) {
    try {
      if (rs != null && !rs.isClosed()) {
        rs.close();
      }
    } catch(SQLException ex) {
      LOG.warn("Failed to close result set " + ex.getMessage());
    }
  }

};