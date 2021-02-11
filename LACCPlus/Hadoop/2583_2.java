//,temp,NameNode.java,955,962,temp,FsDatasetImpl.java,735,743
//,3
public class xxx {
  @Override
  public void getMetrics(MetricsCollector collector, boolean all) {
    try {
      DataNodeMetricHelper.getMetrics(collector, this, "FSDatasetState");
    } catch (Exception e) {
        LOG.warn("Exception thrown while metric collection. Exception : "
          + e.getMessage());
    }
  }

};