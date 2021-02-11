//,temp,TestRegionMover.java,114,142,temp,TestRegionMover.java,91,109
//,3
public class xxx {
  @Test
  public void testWithoutAck() throws Exception {
    MiniHBaseCluster cluster = TEST_UTIL.getHBaseCluster();
    HRegionServer regionServer = cluster.getRegionServer(0);
    String rsName = regionServer.getServerName().getAddress().toString();
    int numRegions = regionServer.getNumberOfOnlineRegions();
    RegionMoverBuilder rmBuilder =
      new RegionMoverBuilder(rsName, TEST_UTIL.getConfiguration()).ack(false);
    try (RegionMover rm = rmBuilder.build()) {
      LOG.info("Unloading " + regionServer.getServerName());
      rm.unload();
      TEST_UTIL.waitFor(30000, 1000, new Predicate<Exception>() {
        @Override
        public boolean evaluate() throws Exception {
          return regionServer.getNumberOfOnlineRegions() == 0;
        }
      });
      LOG.info("Successfully Unloaded\nNow Loading");
      rm.load();
      // In UT we only have 10 regions so it is not likely to fail, so here we check for all
      // regions, in the real production this may not be true.
      TEST_UTIL.waitFor(30000, 1000, new Predicate<Exception>() {
        @Override
        public boolean evaluate() throws Exception {
          return regionServer.getNumberOfOnlineRegions() == numRegions;
        }
      });
    }
  }

};