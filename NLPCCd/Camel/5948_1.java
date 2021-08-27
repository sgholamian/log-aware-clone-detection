//,temp,sample_2669.java,2,16,temp,sample_6030.java,2,18
//,3
public class xxx {
public void writeFile(Exchange exchange, String fileName) throws GenericFileOperationFailedException {
if (endpoint.isAutoCreate()) {
String name = FileUtil.normalizePath(fileName);
File file = new File(name);
String directory = file.getParent();
boolean absolute = FileUtil.isAbsolute(file);
if (directory != null) {
if (!operations.buildDirectory(directory, absolute)) {


log.info("cannot build directory could be because of denied permissions");
}
}
}
}

};