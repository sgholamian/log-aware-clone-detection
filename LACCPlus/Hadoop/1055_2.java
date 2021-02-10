//,temp,TestJobHistoryEvents.java,110,143,temp,TestJobHistoryEvents.java,51,104
//,3
public class xxx {
  @Test
  public void testHistoryEvents() throws Exception {
    Configuration conf = new Configuration();
    MRApp app = new MRAppWithHistory(2, 1, true, this.getClass().getName(), true);
    app.submit(conf);
    Job job = app.getContext().getAllJobs().values().iterator().next();
    JobId jobId = job.getID();
    LOG.info("JOBID is " + TypeConverter.fromYarn(jobId).toString());
    app.waitForState(job, JobState.SUCCEEDED);
    
    //make sure all events are flushed 
    app.waitForState(Service.STATE.STOPPED);
    /*
     * Use HistoryContext to read logged events and verify the number of 
     * completed maps 
    */
    HistoryContext context = new JobHistory();
    // test start and stop states
    ((JobHistory)context).init(conf);
    ((JobHistory)context).start();
    Assert.assertTrue( context.getStartTime()>0);
    Assert.assertEquals(((JobHistory)context).getServiceState(),Service.STATE.STARTED);

    // get job before stopping JobHistory
    Job parsedJob = context.getJob(jobId);

    // stop JobHistory
    ((JobHistory)context).stop();
    Assert.assertEquals(((JobHistory)context).getServiceState(),Service.STATE.STOPPED);


    Assert.assertEquals("CompletedMaps not correct", 2,
        parsedJob.getCompletedMaps());
    Assert.assertEquals(System.getProperty("user.name"), parsedJob.getUserName());
    
    Map<TaskId, Task> tasks = parsedJob.getTasks();
    Assert.assertEquals("No of tasks not correct", 3, tasks.size());
    for (Task task : tasks.values()) {
      verifyTask(task);
    }
    
    Map<TaskId, Task> maps = parsedJob.getTasks(TaskType.MAP);
    Assert.assertEquals("No of maps not correct", 2, maps.size());
    
    Map<TaskId, Task> reduces = parsedJob.getTasks(TaskType.REDUCE);
    Assert.assertEquals("No of reduces not correct", 1, reduces.size());
    
    
    Assert.assertEquals("CompletedReduce not correct", 1,
        parsedJob.getCompletedReduces());
    
    Assert.assertEquals("Job state not currect", JobState.SUCCEEDED,
        parsedJob.getState());
  }

};