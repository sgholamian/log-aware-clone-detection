//,temp,InMemorySCMStore.java,145,164,temp,HBaseTimelineReaderImpl.java,105,120
//,3
public class xxx {
  @Override
  protected void serviceStop() throws Exception {
    LOG.info("Stopping the " + InMemorySCMStore.class.getSimpleName()
        + " service.");
    if (scheduler != null) {
      LOG.info("Shutting down the background thread.");
      scheduler.shutdownNow();
      try {
        if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
          LOG.warn("Gave up waiting for the app check task to shutdown.");
        }
      } catch (InterruptedException e) {
        LOG.warn(
            "The InMemorySCMStore was interrupted while shutting down the "
                + "app check task.", e);
      }
      LOG.info("The background thread stopped.");
    }
    super.serviceStop();
  }

};