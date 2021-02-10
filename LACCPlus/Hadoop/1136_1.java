//,temp,MetricsTimeVaryingInt.java,102,109,temp,MetricsTimeVaryingLong.java,98,105
//,2
public class xxx {
  public synchronized void pushMetric(final MetricsRecord mr) {
    intervalHeartBeat();
    try {
      mr.incrMetric(getName(), getPreviousIntervalValue());
    } catch (Exception e) {
      LOG.info("pushMetric failed for " + getName() + "\n" , e);
    }
  }

};