//,temp,TestFileUtil.java,509,519,temp,TestFileUtil.java,428,438
//,2
public class xxx {
  @Test (timeout = 30000)
  public void testFailFullyDeleteContents() throws IOException {
    if(Shell.WINDOWS) {
      // windows Dir.setWritable(false) does not work for directories
      return;
    }
    LOG.info("Running test to verify failure of fullyDeleteContents()");
    setupDirsAndNonWritablePermissions();
    boolean ret = FileUtil.fullyDeleteContents(new MyFile(del));
    validateAndSetWritablePermissions(true, ret);
  }

};