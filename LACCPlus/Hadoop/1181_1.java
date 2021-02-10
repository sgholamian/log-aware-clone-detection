//,temp,CleanerService.java,108,126,temp,InMemorySCMStore.java,143,162
//,3
public class xxx {
  @Override
  protected void serviceStop() throws Exception {
    LOG.info("Shutting down the background thread.");
    scheduledExecutor.shutdownNow();
    try {
      if (scheduledExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
        LOG.info("The background thread stopped.");
      } else {
        LOG.warn("Gave up waiting for the cleaner task to shutdown.");
      }
    } catch (InterruptedException e) {
      LOG.warn("The cleaner service was interrupted while shutting down the task.",
          e);
    }

    removeGlobalCleanerPidFile();

    super.serviceStop();
  }

};