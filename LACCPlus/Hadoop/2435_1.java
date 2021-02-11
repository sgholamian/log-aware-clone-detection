//,temp,TestRandomOpsWithSnapshots.java,443,467,temp,TestRandomOpsWithSnapshots.java,419,440
//,3
public class xxx {
  private void renameSnapshot() throws IOException {
    if (!pathToSnapshotsMap.isEmpty()) {
      int index = generator.nextInt(pathToSnapshotsMap.size());
      Object[] snapshotPaths = pathToSnapshotsMap.keySet().toArray();
      Path snapshotPath = (Path)snapshotPaths[index];
      ArrayList<String> snapshotNameList =
          pathToSnapshotsMap.get(snapshotPath);

      String snapshotOldName = snapshotNameList.get(
          generator.nextInt(snapshotNameList.size()));

      String snapshotOldNameNoExt = snapshotOldName.substring(0,
          snapshotOldName.lastIndexOf('.')-1);

      String snapshotNewName = snapshotOldNameNoExt + "_rename.ss";
      hdfs.renameSnapshot(snapshotPath, snapshotOldName, snapshotNewName);
      LOG.info("renameSnapshot, directory:" + snapshotPath + ", snapshot name:"
          + snapshotOldName + " to " + snapshotNewName);
      numberSnapshotRenamed++;

      // Adjust pathToSnapshotsMap after snapshot deletion
      pathToSnapshotsMap.get(snapshotPath).remove(snapshotOldName);
      pathToSnapshotsMap.get(snapshotPath).add(snapshotNewName);
    }
  }

};