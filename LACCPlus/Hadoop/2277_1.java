//,temp,S3AUtils.java,1291,1307,temp,AbstractServiceLauncherTestBase.java,152,166
//,3
public class xxx {
  public static void closeAll(Logger log,
      java.io.Closeable... closeables) {
    for (java.io.Closeable c : closeables) {
      if (c != null) {
        try {
          if (log != null) {
            log.debug("Closing {}", c);
          }
          c.close();
        } catch (Exception e) {
          if (log != null && log.isDebugEnabled()) {
            log.debug("Exception in closing {}", c, e);
          }
        }
      }
    }
  }

};