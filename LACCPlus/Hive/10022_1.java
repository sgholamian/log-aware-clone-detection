//,temp,MultiOutputFormat.java,575,582,temp,MultiOutputFormat.java,532,539
//,2
public class xxx {
    @Override
    public void abortTask(TaskAttemptContext taskContext) throws IOException {
      for (String alias : outputCommitters.keySet()) {
        LOGGER.info("Calling abortTask for alias: " + alias);
        BaseOutputCommitterContainer outputContainer = outputCommitters.get(alias);
        outputContainer.getBaseCommitter().abortTask(outputContainer.getContext());
      }
    }

};