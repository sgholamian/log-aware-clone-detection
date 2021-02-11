//,temp,FileOutputCommitter.java,507,521,temp,FileOutputCommitter.java,408,419
//,3
public class xxx {
  @Override
  @Deprecated
  public void cleanupJob(JobContext context) throws IOException {
    if (hasOutputPath()) {
      Path pendingJobAttemptsPath = getPendingJobAttemptsPath();
      FileSystem fs = pendingJobAttemptsPath
          .getFileSystem(context.getConfiguration());
      fs.delete(pendingJobAttemptsPath, true);
    } else {
      LOG.warn("Output Path is null in cleanupJob()");
    }
  }

};