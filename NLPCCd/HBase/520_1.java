//,temp,sample_2664.java,2,15,temp,sample_2662.java,2,16
//,3
public class xxx {
public void testUnloadWithAck() throws Exception {
MiniHBaseCluster cluster = TEST_UTIL.getHBaseCluster();
HRegionServer regionServer = cluster.getRegionServer(0);
String rsName = regionServer.getServerName().getHostname();
int port = regionServer.getServerName().getPort();
String rs = rsName + ":" + Integer.toString(port);
RegionMoverBuilder rmBuilder = new RegionMoverBuilder(rs).ack(true);
RegionMover rm = rmBuilder.build();
rm.setConf(TEST_UTIL.getConfiguration());
rm.unload();


log.info("unloading");
}

};