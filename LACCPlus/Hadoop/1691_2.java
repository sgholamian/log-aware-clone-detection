//,temp,TestWebHdfsFileSystemContract.java,183,192,temp,TestSwiftFileSystemExtendedContract.java,39,50
//,3
public class xxx {
  @Test(timeout = SWIFT_TEST_TIMEOUT)
  public void testOpenNonExistingFile() throws IOException {
    final Path p = new Path("/test/testOpenNonExistingFile");
    //open it as a file, should get FileNotFoundException
    try {
      final FSDataInputStream in = fs.open(p);
      in.close();
      fail("didn't expect to get here");
    } catch (FileNotFoundException fnfe) {
      LOG.debug("Expected: " + fnfe, fnfe);
    }
  }

};