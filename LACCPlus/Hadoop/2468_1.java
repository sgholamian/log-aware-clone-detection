//,temp,TestViewFileSystemLinkFallback.java,184,239,temp,TestViewFileSystemLinkFallback.java,139,182
//,3
public class xxx {
  @Test
  public void testConfLinkFallbackWithRegularLinks() throws Exception {
    Path testBasePath = new Path(TEST_BASE_PATH);
    Path testLevel2Dir = new Path(TEST_BASE_PATH, "dir1/dirA");
    Path testBaseFile = new Path(testBasePath, "testBaseFile.log");
    Path testLevel2File = new Path(testLevel2Dir, "testLevel2File.log");
    fsTarget.mkdirs(testLevel2Dir);

    fsTarget.createNewFile(testBaseFile);
    fsTarget.createNewFile(testLevel2File);
    FSDataOutputStream dataOutputStream = fsTarget.append(testLevel2File);
    dataOutputStream.write("test link fallback".toString().getBytes());
    dataOutputStream.close();

    String clusterName = "ClusterFallback";
    URI viewFsUri = new URI(FsConstants.VIEWFS_SCHEME, clusterName,
        "/", null, null);

    Configuration conf = new Configuration();
    ConfigUtil.addLink(conf, clusterName,
        "/internalDir/linkToDir2",
        new Path(targetTestRoot, "dir2").toUri());
    ConfigUtil.addLink(conf, clusterName,
        "/internalDir/internalDirB/linkToDir3",
        new Path(targetTestRoot, "dir3").toUri());
    ConfigUtil.addLink(conf, clusterName,
        "/danglingLink",
        new Path(targetTestRoot, "missingTarget").toUri());
    ConfigUtil.addLink(conf, clusterName,
        "/linkToAFile",
        new Path(targetTestRoot, "aFile").toUri());
    System.out.println("ViewFs link fallback " + fsTarget.getUri());
    ConfigUtil.addLinkFallback(conf, clusterName, targetTestRoot.toUri());

    FileSystem vfs = FileSystem.get(viewFsUri, conf);
    assertEquals(ViewFileSystem.class, vfs.getClass());
    FileStatus baseFileStat = vfs.getFileStatus(
        new Path(viewFsUri.toString() + testBaseFile.toUri().toString()));
    LOG.info("BaseFileStat: " + baseFileStat);
    Assert.assertEquals("Unexpected file length for " + testBaseFile,
        0, baseFileStat.getLen());
    FileStatus level2FileStat = vfs.getFileStatus(new Path(viewFsUri.toString()
        + testLevel2File.toUri().toString()));
    LOG.info("Level2FileStat: " + level2FileStat);

    dataOutputStream = vfs.append(testLevel2File);
    dataOutputStream.write("Writing via viewfs fallback path".getBytes());
    dataOutputStream.close();

    FileStatus level2FileStatAfterWrite = vfs.getFileStatus(
        new Path(viewFsUri.toString() + testLevel2File.toUri().toString()));
    Assert.assertTrue("Unexpected file length for " + testLevel2File,
        level2FileStatAfterWrite.getLen() > level2FileStat.getLen());

    vfs.close();
  }

};