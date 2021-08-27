//,temp,sample_951.java,2,17,temp,sample_950.java,2,16
//,3
public class xxx {
protected void doReleaseExclusiveReadLock(GenericFileOperations<File> operations, GenericFile<File> file, Exchange exchange) throws Exception {
if (!markerFile) {
return;
}
boolean acquired = exchange.getProperty(asReadLockKey(file, Exchange.FILE_LOCK_FILE_ACQUIRED), false, Boolean.class);
if (acquired) {
String lockFileName = exchange.getProperty(asReadLockKey(file, Exchange.FILE_LOCK_FILE_NAME), String.class);
File lock = new File(lockFileName);
if (lock.exists()) {
boolean deleted = FileUtil.deleteFile(lock);


log.info("lock file was deleted");
}
}
}

};