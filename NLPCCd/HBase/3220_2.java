//,temp,sample_2105.java,2,14,temp,sample_2101.java,2,16
//,3
public class xxx {
public void dummy_method(){
final TableName tableName = TableName.valueOf(name.getMethodName());
Table t = TEST_UTIL.createTable(tableName, Bytes.toBytes("f"), splitKeys);
TEST_UTIL.waitUntilAllRegionsAssigned(tableName);
final int numberOfRegions = admin.getTableRegions(t.getName()).size();
checkIfFavoredNodeInformationIsCorrect(tableName);
byte[] splitPoint = Bytes.toBytes(0);
RegionLocator locator = TEST_UTIL.getConnection().getRegionLocator(tableName);
HRegionInfo parent = locator.getRegionLocation(splitPoint).getRegionInfo();
List<ServerName> parentFN = fnm.getFavoredNodes(parent);
assertNotNull("FN should not be null for region: " + parent, parentFN);


log.info("splitting table");
}

};