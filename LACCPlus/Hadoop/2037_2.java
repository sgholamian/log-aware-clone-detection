//,temp,TestFileSystemApplicationHistoryStore.java,286,306,temp,TestFileSystemApplicationHistoryStore.java,266,284
//,3
public class xxx {
  @Test
  public void testInitExistingWorkingDirectoryInSafeMode() throws Exception {
    LOG.info("Starting testInitExistingWorkingDirectoryInSafeMode");
    tearDown();

    // Setup file system to inject startup conditions
    FileSystem fs = spy(new RawLocalFileSystem());
    doReturn(true).when(fs).isDirectory(any(Path.class));

    try {
      initAndStartStore(fs);
    } catch (Exception e) {
      Assert.fail("Exception should not be thrown: " + e);
    }

    // Make sure that directory creation was not attempted
    verify(fs, times(1)).isDirectory(any(Path.class));
    verify(fs, times(0)).mkdirs(any(Path.class));
  }

};