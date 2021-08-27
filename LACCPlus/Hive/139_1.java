//,temp,HMSHandler.java,10465,10478,temp,HMSHandler.java,10418,10432
//,3
public class xxx {
  @Override
  public void add_replication_metrics(ReplicationMetricList replicationMetricList) throws MetaException{
    startFunction("add_replication_metrics");
    Exception ex = null;
    try {
      getMS().addReplicationMetrics(replicationMetricList);
    } catch (Exception e) {
      LOG.error("Caught exception", e);
      ex = e;
      throw e;
    } finally {
      endFunction("add_replication_metrics", ex == null, ex);
    }
  }

};