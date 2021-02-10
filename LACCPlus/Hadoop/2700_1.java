//,temp,ActivitiesManager.java,156,168,temp,EditLogTailer.java,236,246
//,3
public class xxx {
  @Override
  protected void serviceStop() throws Exception {
    stopped = true;
    if (cleanUpThread != null) {
      cleanUpThread.interrupt();
      try {
        cleanUpThread.join();
      } catch (InterruptedException ie) {
        LOG.warn("Interrupted Exception while stopping", ie);
      }
    }
    super.serviceStop();
  }

};