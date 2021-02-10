//,temp,TestJobHistoryEvents.java,159,192,temp,TestJobHistoryEvents.java,110,143
//,3
public class xxx {
  @Test
  public void testAssignedQueue() throws Exception {
    Configuration conf = new Configuration();
    MRApp app = new MRAppWithHistory(2, 1, true, this.getClass().getName(),
        true, "assignedQueue");
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

    Assert.assertEquals("QueueName not correct", "assignedQueue",
        parsedJob.getQueueName());
  }

};