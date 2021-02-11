//,temp,LeveldbTimelineStore.java,246,260,temp,AMRMClientAsyncImpl.java,104,116
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