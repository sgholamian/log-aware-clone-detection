//,temp,TestSnapshotTemporaryDirectory.java,142,149,temp,TestSnapshotCloneIndependence.java,146,153
//,3
public class xxx {
  @AfterClass public static void cleanupTest() {
    try {
      UTIL.shutdownMiniCluster();
      FileUtils.deleteDirectory(new File(TEMP_DIR));
    } catch (Exception e) {
      LOG.warn("failure shutting down cluster", e);
    }
  }

};