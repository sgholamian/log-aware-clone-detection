//,temp,FederationRMFailoverProxyProvider.java,203,215,temp,SocketIOWithTimeout.java,290,298
//,3
public class xxx {
  private void closeInternal(T currentProxy) {
    if (currentProxy != null) {
      if (currentProxy instanceof Closeable) {
        try {
          ((Closeable) currentProxy).close();
        } catch (IOException e) {
          LOG.warn("Exception while trying to close proxy", e);
        }
      } else {
        RPC.stopProxy(currentProxy);
      }
    }
  }

};