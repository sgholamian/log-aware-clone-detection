//,temp,sample_470.java,2,17,temp,sample_469.java,2,17
//,2
public class xxx {
public void dummy_method(){
String srcFullPath = srcFilePath;
if (!DatastoreFile.isFullDatastorePath(srcFullPath)) srcFullPath = String.format("[%s] %s", srcDsName, srcFilePath);
String destFullPath = destFilePath;
if (!DatastoreFile.isFullDatastorePath(destFullPath)) destFullPath = String.format("[%s] %s", destDsName, destFilePath);
ManagedObjectReference morTask = _context.getService().moveDatastoreFileTask(morFileManager, srcFullPath, morSrcDc, destFullPath, morDestDc, forceOverwrite);
boolean result = _context.getVimClient().waitForTask(morTask);
if (result) {
_context.waitForTaskProgressDone(morTask);
return true;
} else {


log.info("vmware movedatgastorefile task failed due to");
}
}

};