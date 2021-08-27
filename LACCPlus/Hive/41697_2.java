//,temp,TezProcessor.java,127,139,temp,TezProcessor.java,113,125
//,3
public class xxx {
    void scheduleProgressTaskService(long delay, long period) {
      if (!isValid()) {
        LOG.warn("ProgressHelper uninitialized. Bailing on scheduleProgressTaskService()");
        return;
      }
      try {
        progressHelperClass.getDeclaredMethod("scheduleProgressTaskService", long.class, long.class)
            .invoke(progressHelper, delay, period);
        LOG.debug("scheduleProgressTaskService() called!");
      } catch (Exception exception) {
        LOG.warn("Could not scheduleProgressTaskService.", exception);
      }
    }

};