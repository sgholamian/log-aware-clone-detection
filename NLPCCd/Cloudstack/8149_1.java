//,temp,sample_3051.java,2,9,temp,sample_3049.java,2,9
//,3
public class xxx {
public boolean revertToSnapshot(String snapshotName) throws Exception {
ManagedObjectReference morSnapshot = getSnapshotMor(snapshotName);
if (morSnapshot == null) {


log.info("unable to find snapshot");
}
}

};