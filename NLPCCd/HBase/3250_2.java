//,temp,sample_3573.java,2,15,temp,sample_3572.java,2,14
//,3
public class xxx {
public void testSkipFlushTableSnapshot() throws Exception {
SnapshotTestingUtils.assertNoSnapshots(admin);
Table table = UTIL.getConnection().getTable(TABLE_NAME);
UTIL.loadTable(table, TEST_FAM);
UTIL.flush(TABLE_NAME);
UTIL.getHBaseCluster().getMaster().getMasterFileSystem().logFileSystemState(LOG);
String snapshotString = "skipFlushTableSnapshot";
byte[] snapshot = Bytes.toBytes(snapshotString);
admin.snapshot(snapshotString, TABLE_NAME, SnapshotType.SKIPFLUSH);


log.info("snapshot completed");
}

};