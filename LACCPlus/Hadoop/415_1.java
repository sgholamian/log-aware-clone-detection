//,temp,TestCopyMapper.java,729,756,temp,TestCopyMapper.java,499,562
//,3
public class xxx {
  @Test(timeout=40000)
  public void testFileToDir() {
    try {
      deleteState();
      createSourceData();

      FileSystem fs = cluster.getFileSystem();
      CopyMapper copyMapper = new CopyMapper();
      StubContext stubContext = new StubContext(getConfiguration(), null, 0);
      Mapper<Text, CopyListingFileStatus, Text, Text>.Context context
              = stubContext.getContext();

      touchFile(SOURCE_PATH + "/src/file");
      mkdirs(TARGET_PATH + "/src/file");
      try {
        copyMapper.setup(context);
        copyMapper.map(new Text("/src/file"),
            new CopyListingFileStatus(fs.getFileStatus(
              new Path(SOURCE_PATH + "/src/file"))),
            context);
      } catch (IOException e) {
        Assert.assertTrue(e.getMessage().startsWith("Can't replace"));
      }
    } catch (Exception e) {
      LOG.error("Exception encountered ", e);
      Assert.fail("Test failed: " + e.getMessage());
    }
  }

};