//,temp,AbstractS3ACommitter.java,665,674,temp,AliyunOSSFileSystem.java,233,245
//,3
public class xxx {
  private boolean rejectRootDirectoryDelete(boolean isEmptyDir,
      boolean recursive) throws IOException {
    LOG.info("oss delete the {} root directory of {}", bucket, recursive);
    if (isEmptyDir) {
      return true;
    }
    if (recursive) {
      return false;
    } else {
      // reject
      throw new PathIOException(bucket, "Cannot delete root path");
    }
  }

};