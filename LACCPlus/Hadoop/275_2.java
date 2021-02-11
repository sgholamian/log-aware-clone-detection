//,temp,JobImpl.java,1543,1558,temp,JobImpl.java,1524,1541
//,3
public class xxx {
    private void createMapTasks(JobImpl job, long inputLength,
                                TaskSplitMetaInfo[] splits) {
      for (int i=0; i < job.numMapTasks; ++i) {
        TaskImpl task =
            new MapTaskImpl(job.jobId, i,
                job.eventHandler, 
                job.remoteJobConfFile, 
                job.conf, splits[i], 
                job.taskAttemptListener, 
                job.jobToken, job.jobCredentials,
                job.clock,
                job.applicationAttemptId.getAttemptId(),
                job.metrics, job.appContext);
        job.addTask(task);
      }
      LOG.info("Input size for job " + job.jobId + " = " + inputLength
          + ". Number of splits = " + splits.length);
    }

};