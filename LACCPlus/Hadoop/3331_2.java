//,temp,LoadBalancingKMSClientProvider.java,496,506,temp,LoadBalancingKMSClientProvider.java,483,493
//,2
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