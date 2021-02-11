//,temp,sample_3841.java,2,16,temp,sample_3842.java,2,16
//,3
public class xxx {
public void dummy_method(){
TEST_UTIL.waitTableDisabled(tableName.getName());
admin.enableTable(tableName);
TEST_UTIL.waitTableEnabled(tableName);
admin.disableTable(tableName);
TEST_UTIL.waitUntilNoRegionsInTransition(60000);
JVMClusterUtil.RegionServerThread rs = cluster.getRegionServerThreads().get(0);
rs.getRegionServer().stop("stop");
cluster.waitForRegionServerToStop(rs.getRegionServer().getServerName(), 10000);
JVMClusterUtil.RegionServerThread rs2 = cluster.startRegionServer();
cluster.waitForRegionServerToStart(rs2.getRegionServer().getServerName().getHostname(), rs2.getRegionServer().getServerName().getPort(), 60000);


log.info("now enabling table");
}

};