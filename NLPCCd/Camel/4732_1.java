//,temp,sample_4967.java,2,18,temp,sample_2231.java,2,18
//,2
public class xxx {
public void dummy_method(){
if (fastExistsCheck) {
String path = file.getAbsoluteFilePath();
if (path.equals("/") || path.equals("\\")) {
files = operations.listFiles();
} else {
files = operations.listFiles(path);
}
} else {
String path = file.getParent();
if (path.equals("/") || path.equals("\\")) {


log.info("using full directory listing in home directory to update file information consider enabling fastexistscheck option");
}
}
}

};