//,temp,TestRegionMover.java,114,142,temp,TestRegionMover.java,91,109
//,3
public class xxx {
  @Test
  public void testWithAck() throws Exception {
    MiniHBaseCluster cluster = TEST_UTIL.getHBaseCluster();
    HRegionServer regionServer = cluster.getRegionServer(0);
    String rsName = regionServer.getServerName().getAddress().toString();
    int numRegions = regionServer.getNumberOfOnlineRegions();
    RegionMoverBuilder rmBuilder =
      new RegionMoverBuilder(rsName, TEST_UTIL.getConfiguration()).ack(true).maxthreads(8);
    try (RegionMover rm = rmBuilder.build()) {
      LOG.info("Unloading " + regionServer.getServerName());
      rm.unload();
      assertEquals(0, regionServer.getNumberOfOnlineRegions());
      LOG.info("Successfully Unloaded\nNow Loading");
      rm.load();
      assertEquals(numRegions, regionServer.getNumberOfOnlineRegions());
      // Repeat the same load. It should be very fast because all regions are already moved.
      rm.load();
    }
  }

};