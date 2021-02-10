//,temp,DiskBalancer.java,158,171,temp,NodeStateManager.java,587,602
//,3
public class xxx {
  @Override
  public void close() {
    executorService.shutdown();
    try {
      if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
        executorService.shutdownNow();
      }

      if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
        LOG.error("Unable to shutdown NodeStateManager properly.");
      }
    } catch (InterruptedException e) {
      executorService.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }

};