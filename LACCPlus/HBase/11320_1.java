//,temp,IntegrationTestBigLinkedList.java,1448,1464,temp,IntegrationTestBigLinkedList.java,1419,1439
//,3
public class xxx {
    protected boolean verifyUnexpectedValues(Counters counters) {
      final Counter undefined = counters.findCounter(Counts.UNDEFINED);
      final Counter lostfamilies = counters.findCounter(Counts.LOST_FAMILIES);
      boolean success = true;

      if (undefined.getValue() > 0) {
        LOG.error("Found an undefined node. Undefined count=" + undefined.getValue());
        success = false;
      }

      if (lostfamilies.getValue() > 0) {
        LOG.error("Found nodes which lost big or tiny families, count=" + lostfamilies.getValue());
        success = false;
      }

      return success;
    }

};