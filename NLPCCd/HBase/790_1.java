//,temp,sample_3232.java,2,12,temp,sample_3231.java,2,11
//,3
public class xxx {
public void testFlushTableAndRegion() throws Exception {
RegionInfo hri = createTableAndGetOneRegion(tableName);
ServerName serverName = TEST_UTIL.getHBaseCluster().getMaster().getAssignmentManager().getRegionStates() .getRegionServerOfRegion(hri);
HRegionServer regionServer = TEST_UTIL.getHBaseCluster().getLiveRegionServerThreads().stream() .map(rsThread -> rsThread.getRegionServer()) .filter(rs -> rs.getServerName().equals(serverName)).findFirst().get();
ASYNC_CONN.getTable(tableName) .put(new Put(hri.getStartKey()).addColumn(FAMILY, FAMILY_0, Bytes.toBytes("value-1"))) .join();
Assert.assertTrue(regionServer.getOnlineRegion(hri.getRegionName()).getMemStoreSize() > 0);
admin.flushRegion(hri.getRegionName()).get();


log.info("blocking until flush is complete");
}

};