//,temp,TestCounters.java,154,167,temp,TestCounters.java,140,152
//,2
public class xxx {
  private void testMaxCounters(final Counters counters) {
    LOG.info("counters max="+ Limits.getCountersMax());
    for (int i = 0; i < Limits.getCountersMax(); ++i) {
      counters.findCounter("test", "test"+ i);
    }
    setExpected(counters);
    shouldThrow(LimitExceededException.class, new Runnable() {
      public void run() {
        counters.findCounter("test", "bad");
      }
    });
    checkExpected(counters);
  }

};