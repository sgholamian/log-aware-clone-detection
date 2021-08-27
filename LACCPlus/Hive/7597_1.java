//,temp,AcidUtils.java,490,501,temp,Utilities.java,2027,2048
//,3
public class xxx {
  public static Integer parseAttemptId(Path bucketFile) {
    String filename = bucketFile.getName();
    Matcher matcher = BUCKET_PATTERN.matcher(filename);
    Integer attemptId = null;
    if (matcher.matches()) {
      attemptId = matcher.group(2) != null ? Integer.valueOf(matcher.group(2).substring(1)) : null;
    }
    if (Utilities.FILE_OP_LOGGER.isDebugEnabled()) {
      Utilities.FILE_OP_LOGGER.debug("Parsing attempt ID = " + attemptId + " from file name '" + bucketFile + "'");
    }
    return attemptId;
  }

};