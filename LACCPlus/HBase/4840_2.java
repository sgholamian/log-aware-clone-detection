//,temp,TestSnapshotTemporaryDirectory.java,142,149,temp,TestSnapshotCloneIndependence.java,146,153
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