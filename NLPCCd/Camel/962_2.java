//,temp,sample_548.java,2,11,temp,sample_2662.java,2,19
//,3
public class xxx {
public void dummy_method(){
if (endpoint.getFileExist() != GenericFileExist.TryRename && endpoint.isEagerDeleteTargetFile()) {
targetExists = operations.existsFile(target);
if (targetExists) {
if (endpoint.getFileExist() == GenericFileExist.Ignore) {
return;
} else if (endpoint.getFileExist() == GenericFileExist.Fail) {
throw new GenericFileOperationFailedException("File already exist: " + target + ". Cannot write new file.");
} else if (endpoint.getFileExist() == GenericFileExist.Move) {
doMoveExistingFile(target);
} else if (endpoint.isEagerDeleteTargetFile() && endpoint.getFileExist() == GenericFileExist.Override) {


log.info("eagerly deleting existing file");
}
}
}
}

};