//,temp,SchedulerService.java,62,80,temp,AsyncDiskService.java,127,143
//,3
public class xxx {
  @Override
  public void destroy() {
    try {
      long limit = Time.now() + 30 * 1000;
      scheduler.shutdownNow();
      while (!scheduler.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
        LOG.debug("Waiting for scheduler to shutdown");
        if (Time.now() > limit) {
          LOG.warn("Gave up waiting for scheduler to shutdown");
          break;
        }
      }
      if (scheduler.isTerminated()) {
        LOG.debug("Scheduler shutdown");
      }
    } catch (InterruptedException ex) {
      LOG.warn(ex.getMessage(), ex);
    }
  }

};