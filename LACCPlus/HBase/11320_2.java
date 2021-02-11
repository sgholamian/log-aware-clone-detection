//,temp,IntegrationTestBigLinkedList.java,1448,1464,temp,IntegrationTestBigLinkedList.java,1419,1439
//,3
public class xxx {
    protected boolean verifyExpectedValues(long expectedReferenced, Counters counters) {
      final Counter referenced = counters.findCounter(Counts.REFERENCED);
      final Counter unreferenced = counters.findCounter(Counts.UNREFERENCED);
      boolean success = true;

      if (expectedReferenced != referenced.getValue()) {
        LOG.error("Expected referenced count does not match with actual referenced count. " +
            "expected referenced=" + expectedReferenced + " ,actual=" + referenced.getValue());
        success = false;
      }

      if (unreferenced.getValue() > 0) {
        final Counter multiref = counters.findCounter(Counts.EXTRAREFERENCES);
        boolean couldBeMultiRef = (multiref.getValue() == unreferenced.getValue());
        LOG.error("Unreferenced nodes were not expected. Unreferenced count=" + unreferenced.getValue()
            + (couldBeMultiRef ? "; could be due to duplicate random numbers" : ""));
        success = false;
      }

      return success;
    }

};