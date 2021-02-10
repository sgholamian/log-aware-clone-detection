//,temp,MRAppMaster.java,535,563,temp,LocalJobRunner.java,497,521
//,3
public class xxx {
    private org.apache.hadoop.mapreduce.OutputCommitter 
    createOutputCommitter(boolean newApiCommitter, JobID jobId, Configuration conf) throws Exception {
      org.apache.hadoop.mapreduce.OutputCommitter committer = null;

      LOG.info("OutputCommitter set in config "
          + conf.get("mapred.output.committer.class"));

      if (newApiCommitter) {
        org.apache.hadoop.mapreduce.TaskID taskId =
            new org.apache.hadoop.mapreduce.TaskID(jobId, TaskType.MAP, 0);
        org.apache.hadoop.mapreduce.TaskAttemptID taskAttemptID =
            new org.apache.hadoop.mapreduce.TaskAttemptID(taskId, 0);
        org.apache.hadoop.mapreduce.TaskAttemptContext taskContext = 
            new TaskAttemptContextImpl(conf, taskAttemptID);
        OutputFormat outputFormat =
          ReflectionUtils.newInstance(taskContext.getOutputFormatClass(), conf);
        committer = outputFormat.getOutputCommitter(taskContext);
      } else {
        committer = ReflectionUtils.newInstance(conf.getClass(
            "mapred.output.committer.class", FileOutputCommitter.class,
            org.apache.hadoop.mapred.OutputCommitter.class), conf);
      }
      LOG.info("OutputCommitter is " + committer.getClass().getName());
      return committer;
    }

};