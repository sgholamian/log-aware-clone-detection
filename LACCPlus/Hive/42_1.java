//,temp,MultiOutputFormat.java,584,591,temp,MultiOutputFormat.java,562,573
//,3
public class xxx {
    @Override
    public void commitJob(JobContext jobContext) throws IOException {
      for (String alias : outputCommitters.keySet()) {
        LOGGER.info("Calling commitJob for alias: " + alias);
        BaseOutputCommitterContainer outputContainer = outputCommitters.get(alias);
        outputContainer.getBaseCommitter().commitJob(outputContainer.getContext());
      }
    }

};