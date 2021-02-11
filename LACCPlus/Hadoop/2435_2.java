//,temp,TestRandomOpsWithSnapshots.java,443,467,temp,TestRandomOpsWithSnapshots.java,419,440
//,3
public class xxx {
  private void deleteSnapshot() throws IOException {
    if (!pathToSnapshotsMap.isEmpty()) {
      int index = generator.nextInt(pathToSnapshotsMap.size());
      Object[] snapshotPaths = pathToSnapshotsMap.keySet().toArray();
      Path snapshotPath = (Path)snapshotPaths[index];
      ArrayList<String> snapshotNameList=pathToSnapshotsMap.get(snapshotPath);

      String snapshotNameToBeDeleted = snapshotNameList.get(
          generator.nextInt(snapshotNameList.size()));
      hdfs.deleteSnapshot(snapshotPath, snapshotNameToBeDeleted);
      LOG.info("deleteSnapshot, directory: " + snapshotPath +
          ", snapshot name: " + snapshotNameToBeDeleted);
      numberSnapshotDeleted++;

      // Adjust pathToSnapshotsMap after snapshot deletion
      if (snapshotNameList.size() == 1) {
        pathToSnapshotsMap.remove(snapshotPath);
      } else {
        pathToSnapshotsMap.get(snapshotPath).remove(snapshotNameToBeDeleted);
      }
    }
  }

};