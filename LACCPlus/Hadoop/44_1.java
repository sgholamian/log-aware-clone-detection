//,temp,ProtocolHATestBase.java,181,193,temp,AbstractDelegationTokenSecretManager.java,617,633
//,3
public class xxx {
  @After
  public void teardown() throws Exception {
    keepRunning = false;
    if (failoverThread != null) {
      failoverThread.interrupt();
      try {
        failoverThread.join();
      } catch (InterruptedException ex) {
        LOG.error("Error joining with failover thread", ex);
      }
    }
    cluster.stop();
  }

};