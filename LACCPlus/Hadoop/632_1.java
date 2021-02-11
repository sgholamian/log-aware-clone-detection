//,temp,FileOutputCommitter.java,408,419,temp,FileOutputCommitter.java,300,311
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