//,temp,RamDiskAsyncLazyPersistService.java,161,174,temp,AsyncDiskService.java,110,118
//,3
public class xxx {
  synchronized void shutdown() {
    if (executors == null) {
      LOG.warn("AsyncLazyPersistService has already shut down.");
    } else {
      LOG.info("Shutting down all async lazy persist service threads");

      for (Map.Entry<File, ThreadPoolExecutor> e : executors.entrySet()) {
        e.getValue().shutdown();
      }
      // clear the executor map so that calling execute again will fail.
      executors = null;
      LOG.info("All async lazy persist service threads have been shut down");
    }
  }

};