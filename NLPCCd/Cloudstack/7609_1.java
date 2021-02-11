//,temp,sample_114.java,2,19,temp,sample_8430.java,2,19
//,2
public class xxx {
public void dummy_method(){
if (destStore instanceof SwiftTO) {
try {
final String container = "S-" + snapshotTO.getVolume().getVolumeId().toString();
final String destSnapshotName = swiftBackupSnapshot(conn, (SwiftTO) destStore, snapshotSr.getUuid(conn), snapshotBackupUuid, container, false, wait);
final String swiftPath = container + File.separator + destSnapshotName;
finalPath = swiftPath;
} finally {
try {
deleteSnapshotBackup(conn, localMountPoint, folder, secondaryStorageMountPath, snapshotBackupUuid);
} catch (final Exception e) {


log.info("failed to delete snapshot on cache storages");
}
}
}
}

};