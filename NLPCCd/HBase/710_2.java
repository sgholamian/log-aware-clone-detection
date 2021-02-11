//,temp,sample_3841.java,2,16,temp,sample_3842.java,2,16
//,3
public class xxx {
public void dummy_method(){
cluster.waitForRegionServerToStop(rs.getRegionServer().getServerName(), 10000);
JVMClusterUtil.RegionServerThread rs2 = cluster.startRegionServer();
cluster.waitForRegionServerToStart(rs2.getRegionServer().getServerName().getHostname(), rs2.getRegionServer().getServerName().getPort(), 60000);
admin.enableTable(tableName);
assertTrue(admin.isTableEnabled(tableName));
List<HRegionInfo> regions = TEST_UTIL.getAdmin().getTableRegions(tableName);
assertEquals(1, regions.size());
for (HRegionInfo region : regions) {
TEST_UTIL.getAdmin().assign(region.getEncodedNameAsBytes());
}


log.info("waiting for table assigned");
}

};