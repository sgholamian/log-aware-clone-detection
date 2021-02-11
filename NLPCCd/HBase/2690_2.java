//,temp,sample_713.java,2,18,temp,sample_712.java,2,17
//,3
public class xxx {
public void dummy_method(){
assertEquals("HOT", spA);
assertEquals("HOT", spB);
TEST_UTIL.shutdownMiniCluster();
TEST_UTIL.getConfiguration().set(HStore.BLOCK_STORAGE_POLICY_KEY, "WARM");
TEST_UTIL.startMiniCluster();
table = (HTable) TEST_UTIL.createTable(TABLE_NAME, FAMILIES);
regionFs = getHRegionFS(table, conf);
try (Admin admin = TEST_UTIL.getConnection().getAdmin()) {
spA = regionFs.getStoragePolicyName(Bytes.toString(FAMILIES[0]));
spB = regionFs.getStoragePolicyName(Bytes.toString(FAMILIES[1]));


log.info("storage policy of cf");
}
}

};