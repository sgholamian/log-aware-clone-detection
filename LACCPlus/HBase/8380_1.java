//,temp,TestHRegion.java,255,262,temp,TestHRegionReplayEvents.java,153,158
//,3
public class xxx {
  @After
  public void tearDown() throws IOException {
    // Region may have been closed, but it is still no harm if we close it again here using HTU.
    HBaseTestingUtility.closeRegionAndWAL(region);
    EnvironmentEdgeManagerTestHelper.reset();
    LOG.info("Cleaning test directory: " + TEST_UTIL.getDataTestDir());
    TEST_UTIL.cleanupTestDir();
  }

};