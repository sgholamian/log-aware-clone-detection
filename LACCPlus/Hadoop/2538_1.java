//,temp,TestRMContainerAllocator.java,580,642,temp,TestRMContainerAllocator.java,518,578
//,3
public class xxx {
  @Test(timeout = 30000)
  public void testUnconditionalPreemptReducers() throws Exception {
    LOG.info("Running testForcePreemptReducers");

    int forcePreemptThresholdSecs = 2;
    Configuration conf = new Configuration();
    conf.setInt(MRJobConfig.MR_JOB_REDUCER_PREEMPT_DELAY_SEC,
        2 * forcePreemptThresholdSecs);
    conf.setInt(MRJobConfig.MR_JOB_REDUCER_UNCONDITIONAL_PREEMPT_DELAY_SEC,
        forcePreemptThresholdSecs);

    MyResourceManager rm = new MyResourceManager(conf);
    rm.start();
    rm.getMyFifoScheduler().forceResourceLimit(Resource.newInstance(8192, 8));

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
    Assert.assertEquals("The reducer is preeempted too soon", 0,
        assignedRequests.preemptionWaitingReduces.size());

    clock.setTime(clock.getTime() + 1000 * forcePreemptThresholdSecs);
    allocator.preemptReducesIfNeeded();
    Assert.assertEquals("The reducer is not preeempted", 1,
        assignedRequests.preemptionWaitingReduces.size());
  }

};