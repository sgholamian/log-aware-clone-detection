//,temp,LoadBalancingKMSClientProvider.java,330,340,temp,CompositeContext.java,98,108
//,3
public class xxx {
  @InterfaceAudience.Private
  @Override
  protected void flush() throws IOException {
    for (MetricsContext ctxt : subctxt) {
      try {
        ((AbstractMetricsContext)ctxt).flush();
      } catch (IOException e) {
        LOG.warn("flush failed: " + ctxt.getContextName(), e);
      }
    }
  }

};