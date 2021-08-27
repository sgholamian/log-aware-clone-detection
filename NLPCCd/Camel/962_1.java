//,temp,sample_548.java,2,11,temp,sample_2662.java,2,19
//,3
public class xxx {
private boolean doStoreFile(String name, String targetName, Exchange exchange) throws GenericFileOperationFailedException {
if (endpoint.getFileExist() == GenericFileExist.Ignore || endpoint.getFileExist() == GenericFileExist.Fail || endpoint.getFileExist() == GenericFileExist.Move) {
boolean existFile = existsFile(targetName);
if (existFile && endpoint.getFileExist() == GenericFileExist.Ignore) {


log.info("an existing file already exists ignore and do not override it");
}
}
}

};