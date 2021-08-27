//,temp,sample_2661.java,2,20,temp,sample_2666.java,2,20
//,3
public class xxx {
public void dummy_method(){
writeFile(exchange, tempTarget != null ? tempTarget : target);
if (tempTarget != null) {
if (endpoint.getFileExist() != GenericFileExist.TryRename && !endpoint.isEagerDeleteTargetFile()) {
targetExists = operations.existsFile(target);
if (targetExists) {
if (endpoint.getFileExist() == GenericFileExist.Ignore) {
return;
} else if (endpoint.getFileExist() == GenericFileExist.Fail) {
throw new GenericFileOperationFailedException("File already exist: " + target + ". Cannot write new file.");
} else if (endpoint.getFileExist() == GenericFileExist.Override) {


log.info("deleting existing file");
}
}
}
}
}

};