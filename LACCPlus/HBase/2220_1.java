//,temp,TestScannerRetriableFailure.java,108,115,temp,TestFlushSnapshotFromClient.java,128,135
//,2
public class xxx {
  @AfterClass
  public static void tearDown() throws Exception {
    try {
      UTIL.shutdownMiniCluster();
    } catch (Exception e) {
      LOG.warn("failure shutting down cluster", e);
    }
  }

};