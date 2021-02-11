//,temp,TestRMContainerAllocator.java,402,453,temp,TestRMContainerAllocator.java,250,334
//,3
public class xxx {
  @Test(timeout = 30000)
  public void testReducerRampdownDiagnostics() throws Exception {
    LOG.info("Running tesReducerRampdownDiagnostics");

    final Configuration conf = new Configuration();
    conf.setFloat(MRJobConfig.COMPLETED_MAPS_FOR_REDUCE_SLOWSTART, 0.0f);
    final MyResourceManager rm = new MyResourceManager(conf);
    rm.start();
    final DrainDispatcher dispatcher = (DrainDispatcher) rm.getRMContext()
        .getDispatcher();
    final RMApp app = rm.submitApp(1024);
    dispatcher.await();

    final String host = "host1";
    final MockNM nm = rm.registerNode(String.format("%s:1234", host), 2048);
    nm.nodeHeartbeat(true);
    dispatcher.await();
    final ApplicationAttemptId appAttemptId = app.getCurrentAppAttempt()
        .getAppAttemptId();
    rm.sendAMLaunched(appAttemptId);
    dispatcher.await();
    final JobId jobId = MRBuilderUtils
        .newJobId(appAttemptId.getApplicationId(), 0);
    final Job mockJob = mock(Job.class);
    when(mockJob.getReport()).thenReturn(
        MRBuilderUtils.newJobReport(jobId, "job", "user", JobState.RUNNING, 0,
            0, 0, 0, 0, 0, 0, "jobfile", null, false, ""));
    final MyContainerAllocator allocator = new MyContainerAllocator(rm, conf,
        appAttemptId, mockJob);
    // add resources to scheduler
    dispatcher.await();

    // create the container request
    final String[] locations = new String[] { host };
    allocator.sendRequest(createReq(jobId, 0, 1024, locations, false, true));
    for (int i = 0; i < 1;) {
      dispatcher.await();
      i += allocator.schedule().size();
      nm.nodeHeartbeat(true);
    }

    allocator.sendRequest(createReq(jobId, 0, 1024, locations, true, false));
    while (allocator.getTaskAttemptKillEvents().size() == 0) {
      dispatcher.await();
      allocator.schedule().size();
      nm.nodeHeartbeat(true);
    }
    final String killEventMessage = allocator.getTaskAttemptKillEvents().get(0)
        .getMessage();
    Assert.assertTrue("No reducer rampDown preemption message",
        killEventMessage.contains(RMContainerAllocator.RAMPDOWN_DIAGNOSTIC));
  }

};