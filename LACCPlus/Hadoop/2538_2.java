//,temp,TestRMContainerAllocator.java,580,642,temp,TestRMContainerAllocator.java,518,578
//,3
public class xxx {
  @Test(timeout = 30000)
  public void testNonAggressivelyPreemptReducers() throws Exception {
    LOG.info("Running testNonAggressivelyPreemptReducers");

    final int preemptThreshold = 2; //sec
    Configuration conf = new Configuration();
    conf.setInt(
        MRJobConfig.MR_JOB_REDUCER_PREEMPT_DELAY_SEC,
        preemptThreshold);

    MyResourceManager rm = new MyResourceManager(conf);
    rm.start();

    // Submit the application
    RMApp app = rm.submitApp(1024);
    rm.drainEvents();

    MockNM amNodeManager = rm.registerNode("amNM:1234", 2048);
    amNodeManager.nodeHeartbeat(true);
    rm.drainEvents();

    ApplicationAttemptId appAttemptId = app.getCurrentAppAttempt()
        .getAppAttemptId();
    rm.sendAMLaunched(appAttemptId);
    rm.drainEvents();

    JobId jobId = MRBuilderUtils.newJobId(appAttemptId.getApplicationId(), 0);
    Job mockJob = mock(Job.class);
    when(mockJob.getReport()).thenReturn(
        MRBuilderUtils.newJobReport(jobId, "job", "user", JobState.RUNNING, 0,
            0, 0, 0, 0, 0, 0, "jobfile", null, false, ""));
    ControlledClock clock = new ControlledClock(null);
    clock.setTime(1);
    MyContainerAllocator allocator = new MyContainerAllocator(rm, conf,
        appAttemptId, mockJob, clock);
    allocator.setMapResourceRequest(BuilderUtils.newResource(1024, 1));
    allocator.setReduceResourceRequest(BuilderUtils.newResource(1024, 1));
    RMContainerAllocator.AssignedRequests assignedRequests =
        allocator.getAssignedRequests();
    RMContainerAllocator.ScheduledRequests scheduledRequests =
        allocator.getScheduledRequests();
    ContainerRequestEvent event1 =
        createRequest(jobId, 1,
                Resource.newInstance(2048, 1),
                new String[] {"h1"}, false, false);
    scheduledRequests.maps.put(mock(TaskAttemptId.class),
        new RMContainerRequestor.ContainerRequest(event1, null,
                clock.getTime()));
    assignedRequests.reduces.put(mock(TaskAttemptId.class),
        mock(Container.class));

    clock.setTime(clock.getTime() + 1);
    allocator.preemptReducesIfNeeded();
    Assert.assertEquals("The reducer is aggressively preeempted", 0,
        assignedRequests.preemptionWaitingReduces.size());

    clock.setTime(clock.getTime() + (preemptThreshold) * 1000);
    allocator.preemptReducesIfNeeded();
    Assert.assertEquals("The reducer is not preeempted", 1,
            assignedRequests.preemptionWaitingReduces.size());
  }

};