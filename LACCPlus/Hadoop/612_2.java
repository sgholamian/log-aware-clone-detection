//,temp,SchedulerService.java,62,80,temp,AsyncDiskService.java,127,143
//,3
public class xxx {
  public synchronized boolean awaitTermination(long milliseconds) 
      throws InterruptedException {

    long end = Time.now() + milliseconds;
    for (Map.Entry<String, ThreadPoolExecutor> e:
        executors.entrySet()) {
      ThreadPoolExecutor executor = e.getValue();
      if (!executor.awaitTermination(
          Math.max(end - Time.now(), 0),
          TimeUnit.MILLISECONDS)) {
        LOG.warn("AsyncDiskService awaitTermination timeout.");
        return false;
      }
    }
    LOG.info("All AsyncDiskService threads are terminated.");
    return true;
  }

};