//,temp,MultiOutputFormat.java,593,600,temp,MultiOutputFormat.java,541,548
//,3
public class xxx {
    @Override
    public void abortJob(JobContext jobContext, State state) throws IOException {
      for (String alias : outputCommitters.keySet()) {
        LOGGER.info("Calling abortJob for alias: " + alias);
        BaseOutputCommitterContainer outputContainer = outputCommitters.get(alias);
        outputContainer.getBaseCommitter().abortJob(outputContainer.getContext(), state);
      }
    }

};