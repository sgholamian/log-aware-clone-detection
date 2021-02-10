//,temp,TestViewFileSystemLinkFallback.java,184,239,temp,TestViewFileSystemLinkFallback.java,139,182
//,3
public class xxx {
  @Test
  public void testConfLinkFallback() throws Exception {
    Path testBasePath = new Path(TEST_BASE_PATH);
    Path testLevel2Dir = new Path(TEST_BASE_PATH, "dir1/dirA");
    Path testBaseFile = new Path(testBasePath, "testBaseFile.log");
    Path testBaseFileRelative = new Path(testLevel2Dir,
        "../../testBaseFile.log");
    Path testLevel2File = new Path(testLevel2Dir, "testLevel2File.log");
    fsTarget.mkdirs(testLevel2Dir);

    fsTarget.createNewFile(testBaseFile);
    FSDataOutputStream dataOutputStream = fsTarget.append(testBaseFile);
    dataOutputStream.write(1);
    dataOutputStream.close();

    fsTarget.createNewFile(testLevel2File);
    dataOutputStream = fsTarget.append(testLevel2File);
    dataOutputStream.write("test link fallback".toString().getBytes());
    dataOutputStream.close();

    String clusterName = "ClusterFallback";
    URI viewFsUri = new URI(FsConstants.VIEWFS_SCHEME, clusterName,
        "/", null, null);

    Configuration conf = new Configuration();
    ConfigUtil.addLinkFallback(conf, clusterName, fsTarget.getUri());

    FileSystem vfs = FileSystem.get(viewFsUri, conf);
    assertEquals(ViewFileSystem.class, vfs.getClass());
    FileStatus baseFileStat = vfs.getFileStatus(new Path(viewFsUri.toString()
        + testBaseFile.toUri().toString()));
    LOG.info("BaseFileStat: " + baseFileStat);
    FileStatus baseFileRelStat = vfs.getFileStatus(new Path(viewFsUri.toString()
        + testBaseFileRelative.toUri().toString()));
    LOG.info("BaseFileRelStat: " + baseFileRelStat);
    Assert.assertEquals("Unexpected file length for " + testBaseFile,
        1, baseFileStat.getLen());
    Assert.assertEquals("Unexpected file length for " + testBaseFileRelative,
        baseFileStat.getLen(), baseFileRelStat.getLen());
    FileStatus level2FileStat = vfs.getFileStatus(new Path(viewFsUri.toString()
        + testLevel2File.toUri().toString()));
    LOG.info("Level2FileStat: " + level2FileStat);
    vfs.close();
  }

};