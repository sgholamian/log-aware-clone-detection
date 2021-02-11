//,temp,IOUtils.java,275,288,temp,IOUtilsClient.java,34,46
//,3
public class xxx {
  public static void cleanup(Logger log, java.io.Closeable... closeables) {
    for (java.io.Closeable c : closeables) {
      if (c != null) {
        try {
          c.close();
        } catch(Throwable e) {
          if (log != null && log.isDebugEnabled()) {
            log.debug("Exception in closing " + c, e);
          }
        }
      }
    }
  }

};