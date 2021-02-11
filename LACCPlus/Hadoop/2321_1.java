//,temp,TestFileSystemApplicationHistoryStore.java,291,313,temp,TestFileSystemApplicationHistoryStore.java,269,289
//,3
public class xxx {
  @Test
  public void testInitNonExistingWorkingDirectoryInSafeMode() throws Exception {
    LOG.info("Starting testInitNonExistingWorkingDirectoryInSafeMode");
    tearDown();

    // Setup file system to inject startup conditions
    FileSystem fileSystem = spy(new RawLocalFileSystem());
    FileStatus fileStatus = Mockito.mock(FileStatus.class);
    doReturn(false).when(fileStatus).isDirectory();
    doReturn(fileStatus).when(fileSystem).getFileStatus(any(Path.class));
    doThrow(new IOException()).when(fileSystem).mkdirs(any(Path.class));

    try {
      initAndStartStore(fileSystem);
      Assert.fail("Exception should have been thrown");
    } catch (Exception e) {
      // Expected failure
    }

    // Make sure that directory creation was attempted
    verify(fileStatus, never()).isDirectory();
    verify(fileSystem, times(1)).mkdirs(any(Path.class));
  }

};