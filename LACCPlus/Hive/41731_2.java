//,temp,SparkCounters.java,88,97,temp,SparkCounters.java,78,86
//,3
public class xxx {
  public void increment(String groupName, String counterName, long value) {
    SparkCounter counter = getGroup(groupName).getCounter(counterName);
    if (counter == null) {
      LOG.error(
        String.format("counter[%s, %s] has not initialized before.", groupName, counterName));
    } else {
      counter.increment(value);
    }
  }

};