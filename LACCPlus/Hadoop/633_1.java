//,temp,FileOutputCommitter.java,507,521,temp,FileOutputCommitter.java,408,419
//,3
public class xxx {
  @Private
  public void abortTask(TaskAttemptContext context, Path taskAttemptPath) throws IOException {
    if (hasOutputPath()) { 
      context.progress();
      if(taskAttemptPath == null) {
        taskAttemptPath = getTaskAttemptPath(context);
      }
      FileSystem fs = taskAttemptPath.getFileSystem(context.getConfiguration());
      if(!fs.delete(taskAttemptPath, true)) {
        LOG.warn("Could not delete "+taskAttemptPath);
      }
    } else {
      LOG.warn("Output Path is null in abortTask()");
    }
  }

};