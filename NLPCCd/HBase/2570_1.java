//,temp,sample_2659.java,2,15,temp,sample_2665.java,2,16
//,3
public class xxx {
public void testLoadWithAck() throws Exception {
MiniHBaseCluster cluster = TEST_UTIL.getHBaseCluster();
HRegionServer regionServer = cluster.getRegionServer(0);
String rsName = regionServer.getServerName().getHostname();
int port = regionServer.getServerName().getPort();
int noRegions = regionServer.getNumberOfOnlineRegions();
String rs = rsName + ":" + Integer.toString(port);
RegionMoverBuilder rmBuilder = new RegionMoverBuilder(rs).ack(true).maxthreads(8);
RegionMover rm = rmBuilder.build();
rm.setConf(TEST_UTIL.getConfiguration());


log.info("unloading");
}

};