//,temp,sample_4037.java,2,14,temp,sample_4053.java,2,15
//,3
public class xxx {
public static boolean isCopyFile(String filename) {
String taskId = filename;
String copyFileSuffix = null;
int dirEnd = filename.lastIndexOf(Path.SEPARATOR);
if (dirEnd != -1) {
taskId = filename.substring(dirEnd + 1);
}
Matcher m = COPY_FILE_NAME_TO_TASK_ID_REGEX.matcher(taskId);
if (!m.matches()) {


log.info("unable to verify if file name has copy suffix");
}
}

};