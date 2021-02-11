//,temp,TestMasterProcedureWalLease.java,85,92,temp,TestFlushSnapshotFromClient.java,128,135
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