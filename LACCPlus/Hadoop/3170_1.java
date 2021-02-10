//,temp,TestRandomOpsWithSnapshots.java,342,365,temp,TestRandomOpsWithSnapshots.java,321,339
//,3
public class xxx {
  private void deleteTestDir() throws IOException {
    if (snapshottableDirectories.size() > 0) {
      int index = generator.nextInt(snapshottableDirectories.size());
      Path deleteDir = snapshottableDirectories.get(index);

      if (!pathToSnapshotsMap.containsKey(deleteDir)) {
        boolean isWitnessDir = false;
        for (OperationDirectories dir : OperationDirectories.values()) {
          if (dir == OperationDirectories.WitnessDir) {
            isWitnessDir = true;
            deleteDir = new Path(getNewPathString(deleteDir.toString(),
                TESTDIRSTRING, WITNESSDIRSTRING));
          }
          hdfs.delete(deleteDir, true);
          assertFalse("Directory does not exist", hdfs.exists(deleteDir));
          if (!isWitnessDir) {
            snapshottableDirectories.remove(deleteDir);
          }
          LOG.info("Directory removed: " + deleteDir);
          numberDirectoryDeleted++;
        }
      }
    }
  }

};