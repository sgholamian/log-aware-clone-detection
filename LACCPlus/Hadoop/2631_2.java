//,temp,FederationRMFailoverProxyProvider.java,203,215,temp,GraphiteSink.java,110,122
//,3
public class xxx {
    @Override
    public void flush() {
      try {
        graphite.flush();
      } catch (Exception e) {
        LOG.warn("Error flushing metrics to Graphite", e);
        try {
          graphite.close();
        } catch (Exception e1) {
          throw new MetricsException("Error closing connection to Graphite", e1);
        }
      }
    }

};