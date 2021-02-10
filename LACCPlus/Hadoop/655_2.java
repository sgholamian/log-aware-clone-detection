//,temp,MetricsSinkAdapter.java,108,125,temp,MetricsSinkAdapter.java,95,106
//,3
public class xxx {
  boolean putMetrics(MetricsBuffer buffer, long logicalTime) {
    if (logicalTime % period == 0) {
      LOG.debug("enqueue, logicalTime="+ logicalTime);
      if (queue.enqueue(buffer)) {
        refreshQueueSizeGauge();
        return true;
      }
      dropped.incr();
      return false;
    }
    return true; // OK
  }

};