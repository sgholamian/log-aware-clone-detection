//,temp,TestBlockPoolSliceStorage.java,115,134,temp,TestBlockPoolSliceStorage.java,92,109
//,3
public class xxx {
  public void getRestoreDirectoryForBlockFile(String fileName, int nestingLevel) {
    BlockPoolSliceStorage storage = makeBlockPoolStorage();
    final String blockFileSubdir = makeRandomBlockFileSubdir(nestingLevel);
    final String blockFileName = fileName;

    String deletedFilePath =
        storage.getSingularStorageDir().getRoot() + File.separator +
        BlockPoolSliceStorage.TRASH_ROOT_DIR +
        blockFileSubdir + blockFileName;

    String expectedRestorePath =
        storage.getSingularStorageDir().getRoot() + File.separator +
            Storage.STORAGE_DIR_CURRENT +
            blockFileSubdir.substring(0, blockFileSubdir.length() - 1);

    LOG.info("Generated deleted file path " + deletedFilePath);
    assertThat(storage.getRestoreDirectory(new File(deletedFilePath)),
               is(expectedRestorePath));

  }

};