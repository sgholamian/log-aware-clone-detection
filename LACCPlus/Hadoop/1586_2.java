//,temp,FileOutputCommitter.java,552,602,temp,FileOutputCommitter.java,452,496
//,3
public class xxx {
  @Private
  public void commitTask(TaskAttemptContext context, Path taskAttemptPath) 
      throws IOException {

    TaskAttemptID attemptId = context.getTaskAttemptID();
    if (hasOutputPath()) {
      context.progress();
      if(taskAttemptPath == null) {
        taskAttemptPath = getTaskAttemptPath(context);
      }
      FileSystem fs = taskAttemptPath.getFileSystem(context.getConfiguration());
      FileStatus taskAttemptDirStatus;
      try {
        taskAttemptDirStatus = fs.getFileStatus(taskAttemptPath);
      } catch (FileNotFoundException e) {
        taskAttemptDirStatus = null;
      }

      if (taskAttemptDirStatus != null) {
        if (algorithmVersion == 1) {
          Path committedTaskPath = getCommittedTaskPath(context);
          if (fs.exists(committedTaskPath)) {
             if (!fs.delete(committedTaskPath, true)) {
               throw new IOException("Could not delete " + committedTaskPath);
             }
          }
          if (!fs.rename(taskAttemptPath, committedTaskPath)) {
            throw new IOException("Could not rename " + taskAttemptPath + " to "
                + committedTaskPath);
          }
          LOG.info("Saved output of task '" + attemptId + "' to " +
              committedTaskPath);
        } else {
          // directly merge everything from taskAttemptPath to output directory
          mergePaths(fs, taskAttemptDirStatus, outputPath);
          LOG.info("Saved output of task '" + attemptId + "' to " +
              outputPath);
        }
      } else {
        LOG.warn("No Output found for " + attemptId);
      }
    } else {
      LOG.warn("Output Path is null in commitTask()");
    }
  }

};