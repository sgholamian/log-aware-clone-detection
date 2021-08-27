//,temp,sample_4037.java,2,14,temp,sample_4053.java,2,15
//,3
public class xxx {
private static String getIdFromFilename(String filename, Pattern pattern) {
String taskId = filename;
int dirEnd = filename.lastIndexOf(Path.SEPARATOR);
if (dirEnd != -1) {
taskId = filename.substring(dirEnd + 1);
}
Matcher m = pattern.matcher(taskId);
if (!m.matches()) {


log.info("unable to get task id from file name using last component as task id");
}
}

};