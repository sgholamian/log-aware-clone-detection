//,temp,TestRandomOpsWithSnapshots.java,520,548,temp,TestRandomOpsWithSnapshots.java,321,339
//,3
public class xxx {
  private void createTestDir() throws IOException {
    if (snapshottableDirectories.size() > 0) {
      int index = generator.nextInt(snapshottableDirectories.size());
      Path parentDir = snapshottableDirectories.get(index);
      Path newDir = new Path(parentDir, "createTestDir_" +
          UUID.randomUUID().toString());

      for (OperationDirectories dir : OperationDirectories.values()) {
        if (dir == OperationDirectories.WitnessDir) {
          newDir = new Path(getNewPathString(newDir.toString(),
              TESTDIRSTRING, WITNESSDIRSTRING));
        }
        hdfs.mkdirs(newDir);
        assertTrue("Directory exists", hdfs.exists(newDir));
        LOG.info("Directory created: " + newDir);
        numberDirectoryCreated++;
      }
    }
  }

};