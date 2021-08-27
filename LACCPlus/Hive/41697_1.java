//,temp,TezProcessor.java,127,139,temp,TezProcessor.java,113,125
//,3
public class xxx {
    void shutDownProgressTaskService() {
      if (!isValid()) {
        LOG.warn("ProgressHelper uninitialized. Bailing on scheduleProgressTaskService()");
        return;
      }
      try {
        progressHelperClass.getDeclaredMethod("shutDownProgressTaskService").invoke(progressHelper);
        LOG.debug("shutDownProgressTaskService() called!");
      }
      catch (Exception exception) {
        LOG.warn("Could not shutDownProgressTaskService.", exception);
      }
    }

};