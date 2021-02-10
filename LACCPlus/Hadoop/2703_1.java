//,temp,LoadBalancingKMSClientProvider.java,483,493,temp,DomainPeerServer.java,79,86
//,3
public class xxx {
  @Override
  public void close() throws IOException {
    for (KMSClientProvider provider : providers) {
      try {
        provider.close();
      } catch (IOException ioe) {
        LOG.error("Error closing provider with url"
            + "[" + provider.getKMSUrl() + "]");
      }
    }
  }

};