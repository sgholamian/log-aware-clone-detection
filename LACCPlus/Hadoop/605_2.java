//,temp,RamDiskAsyncLazyPersistService.java,161,174,temp,AsyncDiskService.java,110,118
//,3
public class xxx {
  public synchronized void shutdown() {
    
    LOG.info("Shutting down all AsyncDiskService threads...");
    
    for (Map.Entry<String, ThreadPoolExecutor> e
        : executors.entrySet()) {
      e.getValue().shutdown();
    }
  }

};