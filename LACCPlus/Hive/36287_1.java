//,temp,LegacyMetrics.java,98,105,temp,LegacyMetrics.java,89,96
//,2
public class xxx {
    public Long getTimeCounter() {
      try {
        return (Long) metrics.get(timeCounter);
      } catch (JMException e) {
        LOG.warn("Could not find timer value for " + timeCounter + ", returning null instead. ", e);
        return null;
      }
    }

};