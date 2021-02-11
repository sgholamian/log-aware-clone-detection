//,temp,sample_5578.java,2,11,temp,sample_6356.java,2,14
//,3
public class xxx {
private boolean moveFiles() {
File trashDirFile = new File(trashDirectory);
try {
fileIoProvider.mkdirsWithExistsCheck( volume, trashDirFile);
} catch (IOException e) {
return false;
}
if (LOG.isDebugEnabled()) {


log.info("moving files and to trash");
}
}

};