//,temp,TestFileSystemApplicationHistoryStore.java,291,313,temp,TestFileSystemApplicationHistoryStore.java,269,289
//,3
public class xxx {
  @Test
  public void testInitExistingWorkingDirectoryInSafeMode() throws Exception {
    LOG.info("Starting testInitExistingWorkingDirectoryInSafeMode");
    tearDown();

    // Setup file system to inject startup conditions
    FileSystem fileSystem = spy(new RawLocalFileSystem());
    FileStatus fileStatus = Mockito.mock(FileStatus.class);
    doReturn(true).when(fileStatus).isDirectory();
    doReturn(fileStatus).when(fileSystem).getFileStatus(any(Path.class));

    try {
      initAndStartStore(fileSystem);
    } catch (Exception e) {
      Assert.fail("Exception should not be thrown: " + e);
    }

    // Make sure that directory creation was not attempted
    verify(fileStatus, never()).isDirectory();
    verify(fileSystem, times(1)).mkdirs(any(Path.class));
  }

};