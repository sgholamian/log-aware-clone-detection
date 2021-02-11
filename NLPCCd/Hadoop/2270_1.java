//,temp,sample_4245.java,2,11,temp,sample_4243.java,2,10
//,3
public class xxx {
public void getRestoreDirectoryForBlockFile(String fileName, int nestingLevel) {
BlockPoolSliceStorage storage = makeBlockPoolStorage();
final String blockFileSubdir = makeRandomBlockFileSubdir(nestingLevel);
final String blockFileName = fileName;
String deletedFilePath = storage.getSingularStorageDir().getRoot() + File.separator + BlockPoolSliceStorage.TRASH_ROOT_DIR + blockFileSubdir + blockFileName;
String expectedRestorePath = storage.getSingularStorageDir().getRoot() + File.separator + Storage.STORAGE_DIR_CURRENT + blockFileSubdir.substring(0, blockFileSubdir.length() - 1);


log.info("generated deleted file path");
}

};