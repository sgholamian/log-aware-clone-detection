//,temp,sample_2669.java,2,16,temp,sample_6030.java,2,18
//,3
public class xxx {
public void dummy_method(){
String to = endpoint.getMoveExisting().evaluate(dummy, String.class);
to = FileUtil.normalizePath(to);
if (ObjectHelper.isEmpty(to)) {
throw new GenericFileOperationFailedException("moveExisting evaluated as empty String, cannot move existing file: " + fileName);
}
File toFile = new File(to);
String directory = toFile.getParent();
boolean absolute = FileUtil.isAbsolute(toFile);
if (directory != null) {
if (!buildDirectory(directory, absolute)) {


log.info("cannot build directory could be because of denied permissions");
}
}
}

};