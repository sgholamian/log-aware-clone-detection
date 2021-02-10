//,temp,LoadBalancingKMSClientProvider.java,496,506,temp,LoadBalancingKMSClientProvider.java,483,493
//,2
public class xxx {
  @Override
  public void flush() throws IOException {
    for (KMSClientProvider provider : providers) {
      try {
        provider.flush();
      } catch (IOException ioe) {
        LOG.error("Error flushing provider with url"
            + "[" + provider.getKMSUrl() + "]");
      }
    }
  }

};