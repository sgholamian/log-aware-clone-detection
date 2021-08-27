//,temp,sample_1160.java,2,11,temp,sample_6078.java,2,10
//,3
public class xxx {
public boolean acquireExclusiveReadLock(GenericFileOperations<File> operations, GenericFile<File> file, Exchange exchange) throws Exception {
if (!super.acquireExclusiveReadLock(operations, file, exchange)) {
return false;
}
File target = new File(file.getAbsoluteFilePath());


log.info("waiting for exclusive read lock to file");
}

};