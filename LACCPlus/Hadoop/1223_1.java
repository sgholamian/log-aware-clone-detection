//,temp,LoadBalancingKMSClientProvider.java,330,340,temp,CompositeContext.java,98,108
//,3
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