//,temp,LegacyMetrics.java,98,105,temp,LegacyMetrics.java,89,96
//,2
public class xxx {
    public Long getNumCounter() {
      try {
        return (Long) metrics.get(numCounter);
      } catch (JMException e) {
        LOG.warn("Could not find counter value for " + numCounter + ", returning null instead. ", e);
        return null;
      }
    }

};