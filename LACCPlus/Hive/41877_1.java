//,temp,DynamicPartitionFileRecordWriterContainer.java,116,131,temp,DynamicPartitionFileRecordWriterContainer.java,105,114
//,3
public class xxx {
      @Override
      public void commitTask(TaskAttemptContext context) throws IOException {
        for (Map.Entry<String, OutputJobInfo> outputJobInfoEntry : dynamicOutputJobInfo.entrySet()) {
          String dynKey = outputJobInfoEntry.getKey();
          OutputJobInfo outputJobInfo = outputJobInfoEntry.getValue();
          LOG.info("Committing task-attempt for " + outputJobInfo.getLocation());
          TaskAttemptContext dynContext = dynamicContexts.get(dynKey);
          OutputCommitter dynCommitter = baseDynamicCommitters.get(dynKey);
          if (dynCommitter.needsTaskCommit(dynContext)) {
            dynCommitter.commitTask(dynContext);
          }
          else {
            LOG.info("Skipping commitTask() for " + outputJobInfo.getLocation());
          }
        }
      }

};