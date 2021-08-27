//,temp,MultiOutputFormat.java,593,600,temp,MultiOutputFormat.java,541,548
//,3
public class xxx {
    @Override
    public void setupTask(TaskAttemptContext taskContext) throws IOException {
      for (String alias : outputCommitters.keySet()) {
        LOGGER.info("Calling setupTask for alias: " + alias);
        BaseOutputCommitterContainer outputContainer = outputCommitters.get(alias);
        outputContainer.getBaseCommitter().setupTask(outputContainer.getContext());
      }
    }

};