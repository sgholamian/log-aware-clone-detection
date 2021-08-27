//,temp,sample_2661.java,2,20,temp,sample_2666.java,2,20
//,3
public class xxx {
public void dummy_method(){
preWriteCheck();
boolean writeAsTempAndRename = ObjectHelper.isNotEmpty(endpoint.getTempFileName());
String tempTarget = null;
Boolean targetExists;
if (writeAsTempAndRename) {
tempTarget = createTempFileName(exchange, target);
if (endpoint.getFileExist() != GenericFileExist.TryRename && endpoint.isEagerDeleteTargetFile()) {
targetExists = operations.existsFile(target);
if (targetExists) {
if (endpoint.getFileExist() == GenericFileExist.Ignore) {


log.info("an existing file already exists ignore and do not override it");
}
}
}
}
}

};