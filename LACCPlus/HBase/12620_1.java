//,temp,TestSplitTableRegionProcedure.java,109,116,temp,TestSnapshotCloneIndependence.java,146,153
//,2
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