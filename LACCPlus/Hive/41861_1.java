//,temp,LegacyMetrics.java,204,221,temp,LegacyMetrics.java,181,198
//,3
public class xxx {
  public Long decrementCounter(String name, long decrement) {
    Long value = null;
    synchronized(metrics) {
      if (!metrics.hasKey(name)) {
        value = Long.valueOf(decrement);
        set(name, -value);
      } else {
        try {
          value = ((Long)get(name)) - decrement;
          set(name, value);
        } catch (JMException e) {
          LOG.warn("Could not find counter value for " + name
              + ", decrement operation skipped.", e);
        }
      }
    }
    return value;
  }

};