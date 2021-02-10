//,temp,TestRMContainerAllocator.java,2027,2072,temp,TestRMContainerAllocator.java,336,400
//,3
public class xxx {
  @Test
  public void testCompletedTasksRecalculateSchedule() throws Exception {
    LOG.info("Running testCompletedTasksRecalculateSchedule");

    Configuration conf = new Configuration();
    final MyResourceManager rm = new MyResourceManager(conf);
    rm.start();
    DrainDispatcher dispatcher = (DrainDispatcher) rm.getRMContext()
        .getDispatcher();

    // Submit the application
    RMApp app = rm.submitApp(1024);
    dispatcher.await();

    // Make a node to register so as to launch the AM.
    MockNM amNodeManager = rm.registerNode("amNM:1234", 2048);
    amNodeManager.nodeHeartbeat(true);
    dispatcher.await();

    ApplicationAttemptId appAttemptId = app.getCurrentAppAttempt()
        .getAppAttemptId();
    rm.sendAMLaunched(appAttemptId);
    dispatcher.await();

    JobId jobId = MRBuilderUtils.newJobId(appAttemptId.getApplicationId(), 0);
    Job job = mock(Job.class);
    when(job.getReport()).thenReturn(
        MRBuilderUtils.newJobReport(jobId, "job", "user", JobState.RUNNING, 0,
            0, 0, 0, 0, 0, 0, "jobfile", null, false, ""));
    doReturn(10).when(job).getTotalMaps();
    doReturn(10).when(job).getTotalReduces();
    doReturn(0).when(job).getCompletedMaps();
    RecalculateContainerAllocator allocator =
        new RecalculateContainerAllocator(rm, conf, appAttemptId, job);
    allocator.schedule();

    allocator.recalculatedReduceSchedule = false;
    allocator.schedule();
    Assert.assertFalse("Unexpected recalculate of reduce schedule",
        allocator.recalculatedReduceSchedule);

    doReturn(1).when(job).getCompletedMaps();
    allocator.schedule();
    Assert.assertTrue("Expected recalculate of reduce schedule",
        allocator.recalculatedReduceSchedule);
  }

};