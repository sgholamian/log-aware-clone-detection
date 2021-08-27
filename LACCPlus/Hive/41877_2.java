//,temp,DynamicPartitionFileRecordWriterContainer.java,116,131,temp,DynamicPartitionFileRecordWriterContainer.java,105,114
//,3
public class xxx {
      @Override
      public void abortTask(TaskAttemptContext context) throws IOException {
        for (Map.Entry<String, OutputJobInfo> outputJobInfoEntry : dynamicOutputJobInfo.entrySet()) {
          String dynKey = outputJobInfoEntry.getKey();
          OutputJobInfo outputJobInfo = outputJobInfoEntry.getValue();
          LOG.info("Aborting task-attempt for " + outputJobInfo.getLocation());
          baseDynamicCommitters.get(dynKey)
                               .abortTask(dynamicContexts.get(dynKey));
        }
      }

};