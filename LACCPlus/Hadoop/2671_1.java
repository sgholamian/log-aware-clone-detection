//,temp,TestWebHdfsFileSystemContract.java,188,198,temp,TestToken.java,108,119
//,3
public class xxx {
  @Test
  public void testOpenNonExistFile() throws IOException {
    final Path p = new Path("/test/testOpenNonExistFile");
    //open it as a file, should get FileNotFoundException 
    try {
      fs.open(p).read();
      fail("Expected FileNotFoundException was not thrown");
    } catch(FileNotFoundException fnfe) {
      WebHdfsFileSystem.LOG.info("This is expected.", fnfe);
    }
  }

};