//,temp,sample_2105.java,2,14,temp,sample_2101.java,2,16
//,3
public class xxx {
public void testMergeTable() throws Exception {
final TableName tableName = TableName.valueOf(name.getMethodName());
TEST_UTIL.createTable(tableName, Bytes.toBytes("f"), splitKeys);
TEST_UTIL.waitUntilAllRegionsAssigned(tableName);
checkIfFavoredNodeInformationIsCorrect(tableName);
RegionLocator locator = TEST_UTIL.getConnection().getRegionLocator(tableName);
HRegionInfo regionA = locator.getRegionLocation(HConstants.EMPTY_START_ROW).getRegionInfo();
HRegionInfo regionB = locator.getRegionLocation(splitKeys[0]).getRegionInfo();
List<ServerName> regionAFN = fnm.getFavoredNodes(regionA);


log.info("regionb with fn");
}

};