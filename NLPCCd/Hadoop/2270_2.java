//,temp,sample_4245.java,2,11,temp,sample_4243.java,2,10
//,3
public class xxx {
public void getTrashDirectoryForBlockFile(String fileName, int nestingLevel) {
final String blockFileSubdir = makeRandomBlockFileSubdir(nestingLevel);
final String blockFileName = fileName;
String testFilePath = storage.getSingularStorageDir().getRoot() + File.separator + Storage.STORAGE_DIR_CURRENT + blockFileSubdir + blockFileName;
String expectedTrashPath = storage.getSingularStorageDir().getRoot() + File.separator + BlockPoolSliceStorage.TRASH_ROOT_DIR + blockFileSubdir.substring(0, blockFileSubdir.length() - 1);


log.info("got subdir");
}

};