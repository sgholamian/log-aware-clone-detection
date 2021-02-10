//,temp,DistCp.java,451,460,temp,DistCpSync.java,121,130
//,3
public class xxx {
  private static void deleteTargetTmpDir(DistributedFileSystem targetFs,
      Path tmpDir) {
    try {
      if (tmpDir != null) {
        targetFs.delete(tmpDir, true);
      }
    } catch (IOException e) {
      DistCp.LOG.error("Unable to cleanup tmp dir: " + tmpDir, e);
    }
  }

};