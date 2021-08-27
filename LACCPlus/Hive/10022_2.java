//,temp,MultiOutputFormat.java,575,582,temp,MultiOutputFormat.java,532,539
//,2
public class xxx {
    @Override
    public void setupJob(JobContext jobContext) throws IOException {
      for (String alias : outputCommitters.keySet()) {
        LOGGER.info("Calling setupJob for alias: " + alias);
        BaseOutputCommitterContainer outputContainer = outputCommitters.get(alias);
        outputContainer.getBaseCommitter().setupJob(outputContainer.getContext());
      }
    }

};