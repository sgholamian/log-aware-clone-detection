//,temp,ActivitiesManager.java,156,168,temp,RMCommunicator.java,252,270
//,3
public class xxx {
  @Override
  protected void serviceStop() throws Exception {
    if (stopped.getAndSet(true)) {
      // return if already stopped
      return;
    }
    if (allocatorThread != null) {
      allocatorThread.interrupt();
      try {
        allocatorThread.join();
      } catch (InterruptedException ie) {
        LOG.warn("InterruptedException while stopping", ie);
      }
    }
    if (isApplicationMasterRegistered && shouldUnregister) {
      unregister();
    }
    super.serviceStop();
  }

};