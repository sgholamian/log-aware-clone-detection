//,temp,TestRandomOpsWithSnapshots.java,493,517,temp,TestRandomOpsWithSnapshots.java,470,490
//,3
public class xxx {
  private void deleteTestFile() throws IOException {
    if (snapshottableDirectories.size() > 0) {
      int index = generator.nextInt(snapshottableDirectories.size());
      Path randomDir = snapshottableDirectories.get(index);

      FileStatus[] fileStatusList = hdfs.listStatus(randomDir);
      for (FileStatus fsEntry : fileStatusList) {
        if (fsEntry.isFile()) {
          Path deleteFile = fsEntry.getPath();
          for (OperationDirectories dir : OperationDirectories.values()) {
            if (dir == OperationDirectories.WitnessDir) {
              deleteFile = new Path(getNewPathString(deleteFile.toString(),
                  TESTDIRSTRING, WITNESSDIRSTRING));
            }
            hdfs.delete(deleteFile, false);
            assertFalse("File does not exists",
                hdfs.exists(deleteFile));
            LOG.info("deleteTestFile, file deleted: " + deleteFile);
            numberFileDeleted++;
          }
          break;
        }
      }
    }
  }

};