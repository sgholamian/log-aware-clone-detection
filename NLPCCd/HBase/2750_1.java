//,temp,sample_3575.java,2,14,temp,sample_3576.java,2,15
//,3
public class xxx {
public void testFlushTableSnapshotWithProcedure() throws Exception {
SnapshotTestingUtils.assertNoSnapshots(admin);
SnapshotTestingUtils.loadData(UTIL, TABLE_NAME, DEFAULT_NUM_ROWS, TEST_FAM);
UTIL.getHBaseCluster().getMaster().getMasterFileSystem().logFileSystemState(LOG);
String snapshotString = "offlineTableSnapshot";
byte[] snapshot = Bytes.toBytes(snapshotString);
Map<String, String> props = new HashMap<>();
props.put("table", TABLE_NAME.getNameAsString());
admin.execProcedure(SnapshotManager.ONLINE_SNAPSHOT_CONTROLLER_DESCRIPTION, snapshotString, props);


log.info("snapshot completed");
}

};