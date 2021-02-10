//,temp,MetricsSinkAdapter.java,209,221,temp,AllocationFileLoaderService.java,152,164
//,3
public class xxx {
  @Override
  public void serviceStop() throws Exception {
    running = false;
    if (reloadThread != null) {
      reloadThread.interrupt();
      try {
        reloadThread.join(THREAD_JOIN_TIMEOUT_MS);
      } catch (InterruptedException e) {
        LOG.warn("reloadThread fails to join.");
      }
    }
    super.serviceStop();
  }

};