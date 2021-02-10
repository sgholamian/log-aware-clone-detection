//,temp,TestRecovery.java,1159,1310,temp,TestRecovery.java,125,316
//,3
public class xxx {
  @Test
  public void testCrashed() throws Exception {

    int runCount = 0;
    long am1StartTimeEst = System.currentTimeMillis();
    MRApp app = new MRAppWithHistory(2, 1, false, this.getClass().getName(), true, ++runCount);
    Configuration conf = new Configuration();
    conf.setBoolean("mapred.mapper.new-api", true);
    conf.setBoolean("mapred.reducer.new-api", true);
    conf.setBoolean(MRJobConfig.JOB_UBERTASK_ENABLE, false);
    conf.set(FileOutputFormat.OUTDIR, outputDir.toString());
    Job job = app.submit(conf);
    app.waitForState(job, JobState.RUNNING);
    long jobStartTime = job.getReport().getStartTime();
    //all maps would be running
    Assert.assertEquals("No of tasks not correct",
       3, job.getTasks().size());
    Iterator<Task> it = job.getTasks().values().iterator();
    Task mapTask1 = it.next();
    Task mapTask2 = it.next();
    Task reduceTask = it.next();
    
    // all maps must be running
    app.waitForState(mapTask1, TaskState.RUNNING);
    app.waitForState(mapTask2, TaskState.RUNNING);
    
    TaskAttempt task1Attempt1 = mapTask1.getAttempts().values().iterator().next();
    TaskAttempt task2Attempt = mapTask2.getAttempts().values().iterator().next();
    
    //before sending the TA_DONE, event make sure attempt has come to 
    //RUNNING state
    app.waitForState(task1Attempt1, TaskAttemptState.RUNNING);
    app.waitForState(task2Attempt, TaskAttemptState.RUNNING);
    
    // reduces must be in NEW state
    Assert.assertEquals("Reduce Task state not correct",
        TaskState.RUNNING, reduceTask.getReport().getTaskState());

    /////////// Play some games with the TaskAttempts of the first task //////
    //send the fail signal to the 1st map task attempt
    app.getContext().getEventHandler().handle(
        new TaskAttemptEvent(
            task1Attempt1.getID(),
            TaskAttemptEventType.TA_FAILMSG));
    
    app.waitForState(task1Attempt1, TaskAttemptState.FAILED);

    int timeOut = 0;
    while (mapTask1.getAttempts().size() != 2 && timeOut++ < 10) {
      Thread.sleep(2000);
      LOG.info("Waiting for next attempt to start");
    }
    Assert.assertEquals(2, mapTask1.getAttempts().size());
    Iterator<TaskAttempt> itr = mapTask1.getAttempts().values().iterator();
    itr.next();
    TaskAttempt task1Attempt2 = itr.next();
    
    // This attempt will automatically fail because of the way ContainerLauncher
    // is setup
    // This attempt 'disappears' from JobHistory and so causes MAPREDUCE-3846
    app.getContext().getEventHandler().handle(
      new TaskAttemptEvent(task1Attempt2.getID(),
        TaskAttemptEventType.TA_CONTAINER_LAUNCH_FAILED));
    app.waitForState(task1Attempt2, TaskAttemptState.FAILED);

    timeOut = 0;
    while (mapTask1.getAttempts().size() != 3 && timeOut++ < 10) {
      Thread.sleep(2000);
      LOG.info("Waiting for next attempt to start");
    }
    Assert.assertEquals(3, mapTask1.getAttempts().size());
    itr = mapTask1.getAttempts().values().iterator();
    itr.next();
    itr.next();
    TaskAttempt task1Attempt3 = itr.next();
    
    app.waitForState(task1Attempt3, TaskAttemptState.RUNNING);

    //send the kill signal to the 1st map 3rd attempt
    app.getContext().getEventHandler().handle(
        new TaskAttemptEvent(
            task1Attempt3.getID(),
            TaskAttemptEventType.TA_KILL));
    
    app.waitForState(task1Attempt3, TaskAttemptState.KILLED);

    timeOut = 0;
    while (mapTask1.getAttempts().size() != 4 && timeOut++ < 10) {
      Thread.sleep(2000);
      LOG.info("Waiting for next attempt to start");
    }
    Assert.assertEquals(4, mapTask1.getAttempts().size());
    itr = mapTask1.getAttempts().values().iterator();
    itr.next();
    itr.next();
    itr.next();
    TaskAttempt task1Attempt4 = itr.next();
    
    app.waitForState(task1Attempt4, TaskAttemptState.RUNNING);

    //send the done signal to the 1st map 4th attempt
    app.getContext().getEventHandler().handle(
        new TaskAttemptEvent(
            task1Attempt4.getID(),
            TaskAttemptEventType.TA_DONE));

    /////////// End of games with the TaskAttempts of the first task //////

    //wait for first map task to complete
    app.waitForState(mapTask1, TaskState.SUCCEEDED);
    long task1StartTime = mapTask1.getReport().getStartTime();
    long task1FinishTime = mapTask1.getReport().getFinishTime();
    
    //stop the app
    app.stop();

    //rerun
    //in rerun the 1st map will be recovered from previous run
    long am2StartTimeEst = System.currentTimeMillis();
    app = new MRAppWithHistory(2, 1, false, this.getClass().getName(), false, ++runCount);
    conf = new Configuration();
    conf.setBoolean(MRJobConfig.MR_AM_JOB_RECOVERY_ENABLE, true);
    conf.setBoolean("mapred.mapper.new-api", true);
    conf.setBoolean("mapred.reducer.new-api", true);
    conf.set(FileOutputFormat.OUTDIR, outputDir.toString());
    conf.setBoolean(MRJobConfig.JOB_UBERTASK_ENABLE, false);
    job = app.submit(conf);
    app.waitForState(job, JobState.RUNNING);
    //all maps would be running
    Assert.assertEquals("No of tasks not correct",
       3, job.getTasks().size());
    it = job.getTasks().values().iterator();
    mapTask1 = it.next();
    mapTask2 = it.next();
    reduceTask = it.next();
    
    // first map will be recovered, no need to send done
    app.waitForState(mapTask1, TaskState.SUCCEEDED);
    
    app.waitForState(mapTask2, TaskState.RUNNING);
    
    task2Attempt = mapTask2.getAttempts().values().iterator().next();
    //before sending the TA_DONE, event make sure attempt has come to 
    //RUNNING state
    app.waitForState(task2Attempt, TaskAttemptState.RUNNING);
    
    //send the done signal to the 2nd map task
    app.getContext().getEventHandler().handle(
        new TaskAttemptEvent(
            mapTask2.getAttempts().values().iterator().next().getID(),
            TaskAttemptEventType.TA_DONE));
    
    //wait to get it completed
    app.waitForState(mapTask2, TaskState.SUCCEEDED);
    
    //wait for reduce to be running before sending done
    app.waitForState(reduceTask, TaskState.RUNNING);
    //send the done signal to the reduce
    app.getContext().getEventHandler().handle(
        new TaskAttemptEvent(
            reduceTask.getAttempts().values().iterator().next().getID(),
            TaskAttemptEventType.TA_DONE));
    
    app.waitForState(job, JobState.SUCCEEDED);
    app.verifyCompleted();
    Assert.assertEquals("Job Start time not correct",
        jobStartTime, job.getReport().getStartTime());
    Assert.assertEquals("Task Start time not correct",
        task1StartTime, mapTask1.getReport().getStartTime());
    Assert.assertEquals("Task Finish time not correct",
        task1FinishTime, mapTask1.getReport().getFinishTime());
    Assert.assertEquals(2, job.getAMInfos().size());
    int attemptNum = 1;
    // Verify AMInfo
    for (AMInfo amInfo : job.getAMInfos()) {
      Assert.assertEquals(attemptNum++, amInfo.getAppAttemptId()
          .getAttemptId());
      Assert.assertEquals(amInfo.getAppAttemptId(), amInfo.getContainerId()
          .getApplicationAttemptId());
      Assert.assertEquals(MRApp.NM_HOST, amInfo.getNodeManagerHost());
      Assert.assertEquals(MRApp.NM_PORT, amInfo.getNodeManagerPort());
      Assert.assertEquals(MRApp.NM_HTTP_PORT, amInfo.getNodeManagerHttpPort());
    }
    long am1StartTimeReal = job.getAMInfos().get(0).getStartTime();
    long am2StartTimeReal = job.getAMInfos().get(1).getStartTime();
    Assert.assertTrue(am1StartTimeReal >= am1StartTimeEst
        && am1StartTimeReal <= am2StartTimeEst);
    Assert.assertTrue(am2StartTimeReal >= am2StartTimeEst
        && am2StartTimeReal <= System.currentTimeMillis());
    // TODO Add verification of additional data from jobHistory - whatever was
    // available in the failed attempt should be available here
  }

};