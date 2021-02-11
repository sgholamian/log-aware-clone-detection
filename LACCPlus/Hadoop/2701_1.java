//,temp,ActivitiesManager.java,156,168,temp,RMCommunicator.java,252,270
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