//,temp,TestFileSystemApplicationHistoryStore.java,286,306,temp,TestFileSystemApplicationHistoryStore.java,266,284
//,3
public class xxx {
  @Test
  public void testInitNonExistingWorkingDirectoryInSafeMode() throws Exception {
    LOG.info("Starting testInitNonExistingWorkingDirectoryInSafeMode");
    tearDown();

    // Setup file system to inject startup conditions
    FileSystem fs = spy(new RawLocalFileSystem());
    doReturn(false).when(fs).isDirectory(any(Path.class));
    doThrow(new IOException()).when(fs).mkdirs(any(Path.class));

    try {
      initAndStartStore(fs);
      Assert.fail("Exception should have been thrown");
    } catch (Exception e) {
      // Expected failure
    }

    // Make sure that directory creation was attempted
    verify(fs, times(1)).isDirectory(any(Path.class));
    verify(fs, times(1)).mkdirs(any(Path.class));
  }

};