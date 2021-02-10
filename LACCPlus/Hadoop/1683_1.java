//,temp,RMCommunicator.java,253,271,temp,LeveldbTimelineStore.java,246,260
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