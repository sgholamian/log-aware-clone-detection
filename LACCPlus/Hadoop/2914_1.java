//,temp,DiskBalancer.java,158,171,temp,NodeStateManager.java,587,602
//,3
public class xxx {
  private void shutdownExecutor() {
    final int secondsTowait = 10;
    try {
      if (!scheduler.awaitTermination(secondsTowait, TimeUnit.SECONDS)) {
        scheduler.shutdownNow();
        if (!scheduler.awaitTermination(secondsTowait, TimeUnit.SECONDS)) {
          LOG.error("Disk Balancer : Scheduler did not terminate.");
        }
      }
    } catch (InterruptedException ex) {
      scheduler.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }

};