//,temp,RetryCacheMetrics.java,42,48,temp,ApplicationMaster.java,1211,1217
//,3
public class xxx {
  RetryCacheMetrics(RetryCache retryCache) {
    name = "RetryCache."+ retryCache.getCacheName();
    registry = new MetricsRegistry(name);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Initialized "+ registry);
    }
  }

};