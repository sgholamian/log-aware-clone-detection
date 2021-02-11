//,temp,TestFileCreationEmpty.java,44,50,temp,DistCpSync.java,261,270
//,3
public class xxx {
  private void deleteTargetTmpDir(DistributedFileSystem targetFs,
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