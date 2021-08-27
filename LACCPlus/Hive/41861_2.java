//,temp,LegacyMetrics.java,204,221,temp,LegacyMetrics.java,181,198
//,3
public class xxx {
  public Long incrementCounter(String name, long increment) {
    Long value = null;
    synchronized(metrics) {
      if (!metrics.hasKey(name)) {
        value = Long.valueOf(increment);
        set(name, value);
      } else {
        try {
          value = ((Long)get(name)) + increment;
          set(name, value);
        } catch (JMException e) {
          LOG.warn("Could not find counter value for " + name
              + ", increment operation skipped.", e);
        }
      }
    }
    return value;
  }

};