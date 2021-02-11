//,temp,FileOutputCommitter.java,408,419,temp,FileOutputCommitter.java,300,311
//,3
public class xxx {
  public void setupJob(JobContext context) throws IOException {
    if (hasOutputPath()) {
      Path jobAttemptPath = getJobAttemptPath(context);
      FileSystem fs = jobAttemptPath.getFileSystem(
          context.getConfiguration());
      if (!fs.mkdirs(jobAttemptPath)) {
        LOG.error("Mkdirs failed to create " + jobAttemptPath);
      }
    } else {
      LOG.warn("Output Path is null in setupJob()");
    }
  }

};