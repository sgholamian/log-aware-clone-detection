//,temp,TestSafemodeBringsDownMaster.java,70,77,temp,TestSplitRegionWhileRSCrash.java,74,81
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