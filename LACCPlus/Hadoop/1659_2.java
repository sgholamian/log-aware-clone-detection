//,temp,IOUtils.java,240,252,temp,SwiftNativeInputStream.java,217,229
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