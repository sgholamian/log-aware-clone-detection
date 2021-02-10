//,temp,LightWeightGSet.java,87,94,temp,RetryCacheMetrics.java,42,48
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