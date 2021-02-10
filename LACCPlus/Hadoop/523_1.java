//,temp,FsDatasetAsyncDiskService.java,179,193,temp,RamDiskAsyncLazyPersistService.java,161,174
//,2
public class xxx {
  synchronized void shutdown() {
    if (executors == null) {
      LOG.warn("AsyncDiskService has already shut down.");
    } else {
      LOG.info("Shutting down all async disk service threads");
      
      for (Map.Entry<File, ThreadPoolExecutor> e : executors.entrySet()) {
        e.getValue().shutdown();
      }
      // clear the executor map so that calling execute again will fail.
      executors = null;
      
      LOG.info("All async disk service threads have been shut down");
    }
  }

};