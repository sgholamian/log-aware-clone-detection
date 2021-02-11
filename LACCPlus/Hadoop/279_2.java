//,temp,TestBlockPoolSliceStorage.java,115,134,temp,TestBlockPoolSliceStorage.java,92,109
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

    LOG.info("Got subdir " + blockFileSubdir);
    LOG.info("Generated file path " + testFilePath);
    assertThat(storage.getTrashDirectory(new File(testFilePath)), is(expectedTrashPath));
  }

};