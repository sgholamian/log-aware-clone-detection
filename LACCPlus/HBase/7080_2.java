//,temp,TestSnapshotFromClient.java,138,145,temp,TestCreateNamespaceProcedure.java,68,75
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