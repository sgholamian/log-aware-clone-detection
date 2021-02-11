//,temp,MRAppMaster.java,535,563,temp,LocalJobRunner.java,497,521
//,3
public class xxx {
      public OutputCommitter call(Configuration conf) {
        OutputCommitter committer = null;

        LOG.info("OutputCommitter set in config "
            + conf.get("mapred.output.committer.class"));

        if (newApiCommitter) {
          org.apache.hadoop.mapreduce.v2.api.records.TaskId taskID =
              MRBuilderUtils.newTaskId(jobId, 0, TaskType.MAP);
          org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId attemptID =
              MRBuilderUtils.newTaskAttemptId(taskID, 0);
          TaskAttemptContext taskContext = new TaskAttemptContextImpl(conf,
              TypeConverter.fromYarn(attemptID));
          OutputFormat outputFormat;
          try {
            outputFormat = ReflectionUtils.newInstance(taskContext
                .getOutputFormatClass(), conf);
            committer = outputFormat.getOutputCommitter(taskContext);
          } catch (Exception e) {
            throw new YarnRuntimeException(e);
          }
        } else {
          committer = ReflectionUtils.newInstance(conf.getClass(
              "mapred.output.committer.class", FileOutputCommitter.class,
              org.apache.hadoop.mapred.OutputCommitter.class), conf);
        }
        LOG.info("OutputCommitter is " + committer.getClass().getName());
        return committer;
      }

};