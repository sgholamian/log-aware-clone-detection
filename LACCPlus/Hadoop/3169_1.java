//,temp,TestJobHistoryEvents.java,111,144,temp,TestJobHistoryEvents.java,52,105
//,3
public class xxx {
  @Test
  public void testEventsFlushOnStop() throws Exception {

    Configuration conf = new Configuration();
    MRApp app = new MRAppWithSpecialHistoryHandler(1, 0, true, this
        .getClass().getName(), true);
    app.submit(conf);
    Job job = app.getContext().getAllJobs().values().iterator().next();
    JobId jobId = job.getID();
    LOG.info("JOBID is " + TypeConverter.fromYarn(jobId).toString());
    app.waitForState(job, JobState.SUCCEEDED);

    // make sure all events are flushed
    app.waitForState(Service.STATE.STOPPED);
    /*
     * Use HistoryContext to read logged events and verify the number of
     * completed maps
     */
    HistoryContext context = new JobHistory();
    ((JobHistory) context).init(conf);
    Job parsedJob = context.getJob(jobId);
    Assert.assertEquals("CompletedMaps not correct", 1, parsedJob
        .getCompletedMaps());

    Map<TaskId, Task> tasks = parsedJob.getTasks();
    Assert.assertEquals("No of tasks not correct", 1, tasks.size());
    verifyTask(tasks.values().iterator().next());

    Map<TaskId, Task> maps = parsedJob.getTasks(TaskType.MAP);
    Assert.assertEquals("No of maps not correct", 1, maps.size());

    Assert.assertEquals("Job state not currect", JobState.SUCCEEDED,
        parsedJob.getState());
  }

};