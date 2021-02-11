//,temp,sample_3570.java,2,13,temp,sample_3572.java,2,14
//,3
public class xxx {
public void testFlushTableSnapshot() throws Exception {
SnapshotTestingUtils.assertNoSnapshots(admin);
SnapshotTestingUtils.loadData(UTIL, TABLE_NAME, DEFAULT_NUM_ROWS, TEST_FAM);
UTIL.getHBaseCluster().getMaster().getMasterFileSystem().logFileSystemState(LOG);
String snapshotString = "offlineTableSnapshot";
byte[] snapshot = Bytes.toBytes(snapshotString);
admin.snapshot(snapshotString, TABLE_NAME, SnapshotType.FLUSH);
List<SnapshotDescription> snapshots = SnapshotTestingUtils.assertOneSnapshotThatMatches(admin, snapshot, TABLE_NAME);


log.info("fs state after snapshot");
}

};