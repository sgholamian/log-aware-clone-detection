//,temp,sample_947.java,2,16,temp,sample_948.java,2,16
//,3
public class xxx {
public void dummy_method(){
UTIL.loadTable(table, TEST_FAM, false);
FSUtils.logFileSystemState(UTIL.getTestFileSystem(), FSUtils.getRootDir(UTIL.getConfiguration()), LOG);
admin.disableTable(TABLE_NAME);
FSUtils.logFileSystemState(UTIL.getTestFileSystem(), FSUtils.getRootDir(UTIL.getConfiguration()), LOG);
final String SNAPSHOT_NAME = "offlineTableSnapshot";
byte[] snapshot = Bytes.toBytes(SNAPSHOT_NAME);
admin.snapshot(new SnapshotDescription(SNAPSHOT_NAME, TABLE_NAME, SnapshotType.DISABLED, null, -1, SnapshotManifestV1.DESCRIPTOR_VERSION));
List<SnapshotDescription> snapshots = SnapshotTestingUtils.assertOneSnapshotThatMatches(admin, snapshot, TABLE_NAME);
FileSystem fs = UTIL.getHBaseCluster().getMaster().getMasterFileSystem().getFileSystem();
Path rootDir = UTIL.getHBaseCluster().getMaster().getMasterFileSystem().getRootDir();


log.info("fs state after snapshot");
}

};