//,temp,HRegionFileSystem.java,1196,1208,temp,ZKUtil.java,2065,2078
//,3
public class xxx {
  private static void sleepBeforeRetry(String msg, int sleepMultiplier, int baseSleepBeforeRetries,
      int hdfsClientRetriesNumber) throws InterruptedException {
    if (sleepMultiplier > hdfsClientRetriesNumber) {
      if (LOG.isDebugEnabled()) {
        LOG.debug(msg + ", retries exhausted");
      }
      return;
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug(msg + ", sleeping " + baseSleepBeforeRetries + " times " + sleepMultiplier);
    }
    Thread.sleep((long)baseSleepBeforeRetries * sleepMultiplier);
  }

};