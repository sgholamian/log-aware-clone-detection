//,temp,FederationRMFailoverProxyProvider.java,203,215,temp,SocketIOWithTimeout.java,290,298
//,3
public class xxx {
      void close() {
        if (selector != null) {
          try {
            selector.close();
          } catch (IOException e) {
            LOG.warn("Unexpected exception while closing selector : ", e);
          }
        }
      }    

};