//,temp,TestRandomOpsWithSnapshots.java,493,517,temp,TestRandomOpsWithSnapshots.java,470,490
//,3
public class xxx {
  private void createTestFile() throws IOException {
    if (snapshottableDirectories.size() > 0) {
      int index = generator.nextInt(snapshottableDirectories.size());
      Path randomDir = snapshottableDirectories.get(index);
      if (!randomDir.isRoot()) {
        randomDir = randomDir.getParent();
      }

      Path newFile = new Path(randomDir, "createTestFile.log");
      for (OperationDirectories dir : OperationDirectories.values()) {
        if (dir == OperationDirectories.WitnessDir) {
          newFile = new Path(getNewPathString(newFile.toString(),
              TESTDIRSTRING, WITNESSDIRSTRING));
        }
        hdfs.createNewFile(newFile);
        assertTrue("File exists", hdfs.exists(newFile));
        LOG.info("createTestFile, file created: " + newFile);
        numberFileCreated++;
      }
    }
  }

};