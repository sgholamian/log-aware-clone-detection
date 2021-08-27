//,temp,MultiOutputFormat.java,584,591,temp,MultiOutputFormat.java,562,573
//,3
public class xxx {
    @Override
    public void commitTask(TaskAttemptContext taskContext) throws IOException {
      for (String alias : outputCommitters.keySet()) {
        BaseOutputCommitterContainer outputContainer = outputCommitters.get(alias);
        OutputCommitter baseCommitter = outputContainer.getBaseCommitter();
        TaskAttemptContext committerContext = outputContainer.getContext();
        if (baseCommitter.needsTaskCommit(committerContext)) {
          LOGGER.info("Calling commitTask for alias: " + alias);
          baseCommitter.commitTask(committerContext);
        }
      }
    }

};