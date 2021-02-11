//,temp,TestHRegion.java,255,262,temp,TestHRegionReplayEvents.java,153,158
//,3
public class xxx {
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    LOG.info("Cleaning test directory: " + TEST_UTIL.getDataTestDir());
    TEST_UTIL.cleanupTestDir();
    TEST_UTIL.shutdownMiniDFSCluster();
  }

};