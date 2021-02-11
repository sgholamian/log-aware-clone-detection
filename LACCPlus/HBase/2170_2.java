//,temp,TestRecoverStandbyProcedure.java,114,121,temp,TestLockProcedure.java,123,130
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