//,temp,sample_4176.java,2,16,temp,sample_4173.java,2,16
//,3
public class xxx {
public void dummy_method(){
int targetServerNum = (originServerNum + 1) % NUM_RS;
HRegionServer targetServer = cluster.getRegionServer(targetServerNum);
assertFalse(originServer.equals(targetServer));
TEST_UTIL.waitUntilAllRegionsAssigned(table.getName());
hbaseAdmin.move(regionInfo.getEncodedNameAsBytes(), Bytes.toBytes(targetServer.getServerName().getServerName()));
do {
Thread.sleep(1);
} while (cluster.getServerWith(regionInfo.getRegionName()) == originServerNum);
putDataAndVerify(table, "r2", FAMILY, "v2", 2);
TEST_UTIL.waitUntilAllRegionsAssigned(table.getName());


log.info("moving to");
}

};