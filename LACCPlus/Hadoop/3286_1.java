//,temp,TestRMContainerAllocator.java,471,516,temp,TestRMContainerAllocator.java,350,414
//,3
public class xxx {
  @Test(timeout = 30000)
  public void testPreemptReducers() throws Exception {
    LOG.info("Running testPreemptReducers");

    Configuration conf = new Configuration();
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
    MyContainerAllocator allocator = new MyContainerAllocator(rm, conf,
        appAttemptId, mockJob, SystemClock.getInstance());
    allocator.setMapResourceRequest(BuilderUtils.newResource(1024, 1));
    allocator.setReduceResourceRequest(BuilderUtils.newResource(1024, 1));
    RMContainerAllocator.AssignedRequests assignedRequests =
        allocator.getAssignedRequests();
    RMContainerAllocator.ScheduledRequests scheduledRequests =
        allocator.getScheduledRequests();
    ContainerRequestEvent event1 =
        createRequest(jobId, 1, Resource.newInstance(2048, 1),
            new String[] {"h1"}, false, false);
    scheduledRequests.maps.put(mock(TaskAttemptId.class),
        new RMContainerRequestor.ContainerRequest(event1, null, null));
    assignedRequests.reduces.put(mock(TaskAttemptId.class),
        mock(Container.class));

    allocator.preemptReducesIfNeeded();
    Assert.assertEquals("The reducer is not preempted",
        1, assignedRequests.preemptionWaitingReduces.size());
  }

};