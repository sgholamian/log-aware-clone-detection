//,temp,AcidUtils.java,490,501,temp,Utilities.java,2027,2048
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
      LOG.warn("Unable to verify if file name {} has _copy_ suffix.", filename);
    } else {
      taskId = m.group(1);
      copyFileSuffix = m.group(4);
    }

    LOG.debug("Filename: {} TaskId: {} CopySuffix: {}", filename, taskId, copyFileSuffix);
    if (taskId != null && copyFileSuffix != null) {
      return true;
    }

    return false;
  }

};