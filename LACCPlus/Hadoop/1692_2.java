//,temp,ProtocolHATestBase.java,181,193,temp,ApplicationMasterLauncher.java,97,106
//,3
public class xxx {
  @Override
  protected void serviceStop() throws Exception {
    launcherHandlingThread.interrupt();
    try {
      launcherHandlingThread.join();
    } catch (InterruptedException ie) {
      LOG.info(launcherHandlingThread.getName() + " interrupted during join ", 
          ie);    }
    launcherPool.shutdown();
  }

};