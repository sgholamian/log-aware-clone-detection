//,temp,IOUtils.java,275,288,temp,NativeAzureFileSystemHelper.java,51,61
//,3
public class xxx {
  public static void cleanupWithLogger(Logger logger,
      java.io.Closeable... closeables) {
    for (java.io.Closeable c : closeables) {
      if (c != null) {
        try {
          c.close();
        } catch (Throwable e) {
          if (logger != null) {
            logger.debug("Exception in closing {}", c, e);
          }
        }
      }
    }
  }

};