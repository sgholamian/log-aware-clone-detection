//,temp,sample_2661.java,2,15,temp,sample_2665.java,2,16
//,3
public class xxx {
public void dummy_method(){
fos.write(excludeServerName);
fos.close();
HRegionServer regionServer = cluster.getRegionServer(0);
String rsName = regionServer.getServerName().getHostname();
int port = regionServer.getServerName().getPort();
String rs = rsName + ":" + Integer.toString(port);
RegionMoverBuilder rmBuilder = new RegionMoverBuilder(rs).ack(true).excludeFile(excludeFile.getCanonicalPath());
RegionMover rm = rmBuilder.build();
rm.setConf(TEST_UTIL.getConfiguration());
rm.unload();


log.info("unloading");
}

};