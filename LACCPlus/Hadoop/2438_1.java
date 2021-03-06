//,temp,FileOutputCommitter.java,679,729,temp,FileOutputCommitter.java,563,618
//,3
public class xxx {
  @Override
  public void recoverTask(TaskAttemptContext context)
      throws IOException {
    if(hasOutputPath()) {
      context.progress();
      TaskAttemptID attemptId = context.getTaskAttemptID();
      int previousAttempt = getAppAttemptId(context) - 1;
      if (previousAttempt < 0) {
        throw new IOException ("Cannot recover task output for first attempt...");
      }

      Path previousCommittedTaskPath = getCommittedTaskPath(
          previousAttempt, context);
      FileSystem fs = previousCommittedTaskPath.getFileSystem(context.getConfiguration());
      if (LOG.isDebugEnabled()) {
        LOG.debug("Trying to recover task from " + previousCommittedTaskPath);
      }
      if (algorithmVersion == 1) {
        if (fs.exists(previousCommittedTaskPath)) {
          Path committedTaskPath = getCommittedTaskPath(context);
          if (!fs.delete(committedTaskPath, true) &&
              fs.exists(committedTaskPath)) {
            throw new IOException("Could not delete " + committedTaskPath);
          }
          //Rename can fail if the parent directory does not yet exist.
          Path committedParent = committedTaskPath.getParent();
          fs.mkdirs(committedParent);
          if (!fs.rename(previousCommittedTaskPath, committedTaskPath)) {
            throw new IOException("Could not rename " + previousCommittedTaskPath +
                " to " + committedTaskPath);
          }
        } else {
            LOG.warn(attemptId+" had no output to recover.");
        }
      } else {
        // essentially a no-op, but for backwards compatibility
        // after upgrade to the new fileOutputCommitter,
        // check if there are any output left in committedTaskPath
        try {
          FileStatus from = fs.getFileStatus(previousCommittedTaskPath);
          LOG.info("Recovering task for upgrading scenario, moving files from "
              + previousCommittedTaskPath + " to " + outputPath);
          mergePaths(fs, from, outputPath);
        } catch (FileNotFoundException ignored) {
        }
        LOG.info("Done recovering task " + attemptId);
      }
    } else {
      LOG.warn("Output Path is null in recoverTask()");
    }
  }

};