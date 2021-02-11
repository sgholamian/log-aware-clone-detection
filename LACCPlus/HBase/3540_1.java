//,temp,TestSnapshotMetadata.java,110,117,temp,TestMasterFailoverWithProcedures.java,85,92
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