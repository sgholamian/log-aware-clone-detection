//,temp,HMSHandler.java,10480,10494,temp,HMSHandler.java,10401,10416
//,3
public class xxx {
  @Override
  public ReplicationMetricList get_replication_metrics(GetReplicationMetricsRequest
                                                           getReplicationMetricsRequest) throws MetaException{
    startFunction("get_replication_metrics");
    Exception ex = null;
    try {
      return getMS().getReplicationMetrics(getReplicationMetricsRequest);
    } catch (Exception e) {
      LOG.error("Caught exception", e);
      ex = e;
      throw e;
    } finally {
      endFunction("get_replication_metrics", ex == null, ex);
    }
  }

};