//,temp,JobImpl.java,1543,1558,temp,JobImpl.java,1524,1541
//,3
public class xxx {
    private void createReduceTasks(JobImpl job) {
      for (int i = 0; i < job.numReduceTasks; i++) {
        TaskImpl task =
            new ReduceTaskImpl(job.jobId, i,
                job.eventHandler, 
                job.remoteJobConfFile, 
                job.conf, job.numMapTasks, 
                job.taskAttemptListener, job.jobToken,
                job.jobCredentials, job.clock,
                job.applicationAttemptId.getAttemptId(),
                job.metrics, job.appContext);
        job.addTask(task);
      }
      LOG.info("Number of reduces for job " + job.jobId + " = "
          + job.numReduceTasks);
    }

};