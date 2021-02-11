//,temp,TestCounters.java,154,167,temp,TestCounters.java,140,152
//,2
public class xxx {
  private void testMaxGroups(final Counters counters) {
    LOG.info("counter groups max="+ Limits.getGroupsMax());
    for (int i = 0; i < Limits.getGroupsMax(); ++i) {
      // assuming COUNTERS_MAX > GROUPS_MAX
      counters.findCounter("test"+ i, "test");
    }
    setExpected(counters);
    shouldThrow(LimitExceededException.class, new Runnable() {
      public void run() {
        counters.findCounter("bad", "test");
      }
    });
    checkExpected(counters);
  }

};