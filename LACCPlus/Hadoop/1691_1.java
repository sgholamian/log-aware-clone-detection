//,temp,TestWebHdfsFileSystemContract.java,183,192,temp,TestSwiftFileSystemExtendedContract.java,39,50
//,3
public class xxx {
  public void testOpenNonExistFile() throws IOException {
    final Path p = new Path("/test/testOpenNonExistFile");
    //open it as a file, should get FileNotFoundException 
    try {
      fs.open(p);
      fail("Expected FileNotFoundException was not thrown");
    } catch(FileNotFoundException fnfe) {
      WebHdfsFileSystem.LOG.info("This is expected.", fnfe);
    }
  }

};