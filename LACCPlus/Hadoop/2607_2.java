//,temp,LeveldbTimelineStore.java,274,288,temp,NodeResourceMonitorImpl.java,111,122
//,3
public class xxx {
  @Override
  protected void serviceStop() throws Exception {
    if (this.isEnabled()) {
      this.monitoringThread.interrupt();
      try {
        this.monitoringThread.join(10 * 1000);
      } catch (InterruptedException e) {
        LOG.warn("Could not wait for the thread to join");
      }
    }
    super.serviceStop();
  }

};