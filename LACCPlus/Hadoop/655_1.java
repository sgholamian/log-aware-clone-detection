//,temp,MetricsSinkAdapter.java,108,125,temp,MetricsSinkAdapter.java,95,106
//,3
public class xxx {
  public boolean putMetricsImmediate(MetricsBuffer buffer) {
    WaitableMetricsBuffer waitableBuffer =
        new WaitableMetricsBuffer(buffer);
    if (queue.enqueue(waitableBuffer)) {
      refreshQueueSizeGauge();
    } else {
      LOG.warn(name + " has a full queue and can't consume the given metrics.");
      dropped.incr();
      return false;
    }
    if (!waitableBuffer.waitTillNotified(oobPutTimeout)) {
      LOG.warn(name +
          " couldn't fulfill an immediate putMetrics request in time." +
          " Abandoning.");
      return false;
    }
    return true;
  }

};