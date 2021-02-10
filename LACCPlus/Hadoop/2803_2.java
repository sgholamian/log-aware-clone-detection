//,temp,IOUtils.java,253,266,temp,NativeAzureFileSystemHelper.java,51,61
//,3
public class xxx {
  public static void cleanup(Logger log, java.io.Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch(IOException e) {
        if (log != null) {
          log.debug("Exception in closing {}", closeable, e);
        }
      }
    }
  }

};