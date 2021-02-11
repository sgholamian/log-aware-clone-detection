//,temp,sample_713.java,2,18,temp,sample_712.java,2,17
//,3
public class xxx {
public void dummy_method(){
try (Admin admin = TEST_UTIL.getConnection().getAdmin()) {
spA = regionFs.getStoragePolicyName(Bytes.toString(FAMILIES[0]));
spB = regionFs.getStoragePolicyName(Bytes.toString(FAMILIES[1]));
assertEquals("WARM", spA);
assertEquals("WARM", spB);
HColumnDescriptor hcdA = new HColumnDescriptor(Bytes.toString(FAMILIES[0]));
hcdA.setValue(HStore.BLOCK_STORAGE_POLICY_KEY, "ONE_SSD");
admin.modifyColumnFamily(TABLE_NAME, hcdA);
while (TEST_UTIL.getMiniHBaseCluster().getMaster().getAssignmentManager(). getRegionStates().hasRegionsInTransition()) {
Thread.sleep(200);


log.info("waiting on table to finish schema altering");
}
}
}

};