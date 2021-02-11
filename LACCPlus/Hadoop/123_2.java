//,temp,RetriableFileCopyCommand.java,227,235,temp,CopyCommitter.java,117,130
//,3
public class xxx {
  private void cleanupTempFiles(JobContext context) {
    try {
      Configuration conf = context.getConfiguration();

      Path targetWorkPath = new Path(conf.get(DistCpConstants.CONF_LABEL_TARGET_WORK_PATH));
      FileSystem targetFS = targetWorkPath.getFileSystem(conf);

      String jobId = context.getJobID().toString();
      deleteAttemptTempFiles(targetWorkPath, targetFS, jobId);
      deleteAttemptTempFiles(targetWorkPath.getParent(), targetFS, jobId);
    } catch (Throwable t) {
      LOG.warn("Unable to cleanup temp files", t);
    }
  }

};