//,temp,TestRandomOpsWithSnapshots.java,520,548,temp,TestRandomOpsWithSnapshots.java,321,339
//,3
public class xxx {
  private void renameTestFile() throws IOException {
    if (snapshottableDirectories.size() > 0) {
      int index = generator.nextInt(snapshottableDirectories.size());
      Path randomDir = snapshottableDirectories.get(index);

      FileStatus[] fileStatusList = hdfs.listStatus(randomDir);
      for (FileStatus fsEntry : fileStatusList) {
        if (fsEntry.isFile()) {
          Path oldFile = fsEntry.getPath();
          Path newFile = oldFile.suffix("_renameFile");
          for (OperationDirectories dir : OperationDirectories.values()) {
            if (dir == OperationDirectories.WitnessDir) {
              oldFile = new Path(getNewPathString(oldFile.toString(),
                  TESTDIRSTRING, WITNESSDIRSTRING));
              newFile = new Path(getNewPathString(newFile.toString(),
                  TESTDIRSTRING, WITNESSDIRSTRING));
            }

            hdfs.rename(oldFile, newFile, Options.Rename.OVERWRITE);
            assertTrue("Target file exists", hdfs.exists(newFile));
            assertFalse("Source file does not exist", hdfs.exists(oldFile));
            LOG.info("Renamed file: " + oldFile + " to file: " + newFile);
            numberFileRenamed++;
          }
          break;
        }
      }
    }
  }

};