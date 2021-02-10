//,temp,AbstractS3ACommitter.java,665,674,temp,AliyunOSSFileSystem.java,233,245
//,3
public class xxx {
  protected void maybeIgnore(
      boolean suppress,
      String action,
      IOException ex) throws IOException {
    if (suppress) {
      LOG.info(action, ex);
    } else {
      throw ex;
    }
  }

};