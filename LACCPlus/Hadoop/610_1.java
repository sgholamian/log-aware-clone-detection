//,temp,AMRMClientAsyncImpl.java,104,116,temp,AllocationFileLoaderService.java,152,164
//,3
public class xxx {
  @Override
  protected void serviceStop() throws Exception {
    keepRunning = false;
    heartbeatThread.interrupt();
    try {
      heartbeatThread.join();
    } catch (InterruptedException ex) {
      LOG.error("Error joining with heartbeat thread", ex);
    }
    client.stop();
    handlerThread.interrupt();
    super.serviceStop();
  }

};