//,temp,TestBlockPoolSliceStorage.java,121,140,temp,TestBlockPoolSliceStorage.java,95,115
//,3
public class xxx {
  public void getTrashDirectoryForBlockFile(String fileName, int nestingLevel) {
    final String blockFileSubdir = makeRandomBlockFileSubdir(nestingLevel);
    final String blockFileName = fileName;

    String testFilePath =
        storage.getSingularStorageDir().getRoot() + File.separator +
            Storage.STORAGE_DIR_CURRENT +
            blockFileSubdir + blockFileName;

    String expectedTrashPath =
        storage.getSingularStorageDir().getRoot() + File.separator +
            BlockPoolSliceStorage.TRASH_ROOT_DIR +
            blockFileSubdir.substring(0, blockFileSubdir.length() - 1);

    LOG.info("Got subdir {}", blockFileSubdir);
    LOG.info("Generated file path {}", testFilePath);

    ReplicaInfo info = Mockito.mock(ReplicaInfo.class);
    Mockito.when(info.getBlockURI()).thenReturn(new File(testFilePath).toURI());
    assertThat(storage.getTrashDirectory(info), is(expectedTrashPath));
  }

};