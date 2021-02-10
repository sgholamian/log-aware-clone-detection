//,temp,LocalJobRunner.java,298,333,temp,LocalJobRunner.java,220,253
//,3
public class xxx {
      public void run() {
        try {
          TaskAttemptID reduceId = new TaskAttemptID(new TaskID(
              jobId, TaskType.REDUCE, taskId), 0);
          LOG.info("Starting task: " + reduceId);

          ReduceTask reduce = new ReduceTask(systemJobFile.toString(),
              reduceId, taskId, mapIds.size(), 1);
          reduce.setUser(UserGroupInformation.getCurrentUser().
              getShortUserName());
          setupChildMapredLocalDirs(reduce, localConf);
          reduce.setLocalMapFiles(mapOutputFiles);

          if (!Job.this.isInterrupted()) {
            reduce.setJobFile(localJobFile.toString());
            localConf.setUser(reduce.getUser());
            reduce.localizeConfiguration(localConf);
            reduce.setConf(localConf);
            try {
              reduce_tasks.getAndIncrement();
              myMetrics.launchReduce(reduce.getTaskID());
              reduce.run(localConf, Job.this);
              myMetrics.completeReduce(reduce.getTaskID());
            } finally {
              reduce_tasks.getAndDecrement();
            }

            LOG.info("Finishing task: " + reduceId);
          } else {
            throw new InterruptedException();
          }
        } catch (Throwable t) {
          // store this to be rethrown in the initial thread context.
          this.storedException = t;
        }
      }

};