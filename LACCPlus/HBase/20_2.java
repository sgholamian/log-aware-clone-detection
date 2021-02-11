//,temp,TestSnapshotTemporaryDirectory.java,142,149,temp,TestFlushSnapshotFromClient.java,128,135
//,3
public class xxx {
  @AfterClass
  public static void cleanupTest() throws Exception {
    try {
      UTIL.shutdownMiniCluster();
    } catch (Exception e) {
      LOG.warn("failure shutting down cluster", e);
    }
  }

};