//,temp,SwiftNativeInputStream.java,221,233,temp,IOUtilsClient.java,34,46
//,3
public class xxx {
  private void innerClose(String reason) throws IOException {
    try {
      if (httpStream != null) {
        reasonClosed = reason;
        if (LOG.isDebugEnabled()) {
          LOG.debug("Closing HTTP input stream : " + reason);
        }
        httpStream.close();
      }
    } finally {
      httpStream = null;
    }
  }

};