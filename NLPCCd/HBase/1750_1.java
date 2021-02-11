//,temp,sample_2666.java,2,16,temp,sample_2664.java,2,15
//,3
public class xxx {
public void dummy_method(){
HRegionServer regionServer = cluster.getRegionServer(0);
String rsName = regionServer.getServerName().getHostname();
int port = regionServer.getServerName().getPort();
String rs = rsName + ":" + Integer.toString(port);
RegionMoverBuilder rmBuilder = new RegionMoverBuilder(rs).ack(true).excludeFile(excludeFile.getCanonicalPath());
RegionMover rm = rmBuilder.build();
rm.setConf(TEST_UTIL.getConfiguration());
rm.unload();
assertEquals(0, regionServer.getNumberOfOnlineRegions());
assertEquals(regionsExcludeServer, cluster.getRegionServer(1).getNumberOfOnlineRegions());


log.info("before after");
}

};