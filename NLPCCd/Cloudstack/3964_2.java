//,temp,sample_118.java,2,16,temp,sample_117.java,2,16
//,2
public class xxx {
public void dummy_method(){
final String volumeUuid = snapshotTO.getVolume().getPath();
destroySnapshotOnPrimaryStorageExceptThis(conn, volumeUuid, snapshotUuid);
final SnapshotObjectTO newSnapshot = new SnapshotObjectTO();
newSnapshot.setPath(finalPath);
newSnapshot.setPhysicalSize(physicalSize);
if (fullbackup) {
newSnapshot.setParentSnapshotPath(null);
} else {
newSnapshot.setParentSnapshotPath(prevBackupUuid);
}


log.info("new snapshot details");
}

};