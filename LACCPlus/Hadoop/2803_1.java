//,temp,IOUtils.java,253,266,temp,NativeAzureFileSystemHelper.java,51,61
//,3
public class xxx {
  @Deprecated
  public static void cleanup(Log log, java.io.Closeable... closeables) {
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