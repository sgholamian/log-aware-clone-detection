//,temp,sample_7361.java,2,18,temp,sample_558.java,2,18
//,2
public class xxx {
public void dummy_method(){
to = endpoint.getConfiguration().normalizePath(to);
if (ObjectHelper.isEmpty(to)) {
throw new GenericFileOperationFailedException("moveExisting evaluated as empty String, cannot move existing file: " + name);
}
String dir = FileUtil.onlyPath(to);
if (dir != null) {
buildDirectory(dir, false);
}
if (existsFile(to)) {
if (endpoint.isEagerDeleteTargetFile()) {


log.info("deleting existing file");
}
}
}

};