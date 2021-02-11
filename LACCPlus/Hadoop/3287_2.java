//,temp,TestRMContainerAllocator.java,871,948,temp,TestRMContainerAllocator.java,350,414
//,3
public class xxx {
  @Test
  public void testResource() throws Exception {

    LOG.info("Running testResource");

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
        appAttemptId, mockJob);

    // add resources to scheduler
    MockNM nodeManager1 = rm.registerNode("h1:1234", 10240);
    MockNM nodeManager2 = rm.registerNode("h2:1234", 10240);
    MockNM nodeManager3 = rm.registerNode("h3:1234", 10240);
    rm.drainEvents();

    // create the container request
    ContainerRequestEvent event1 = ContainerRequestCreator.createRequest(
            jobId, 1, Resource.newInstance(1024, 1),
        new String[] {"h1"});
    allocator.sendRequest(event1);

    // send 1 more request with different resource req
    ContainerRequestEvent event2 = ContainerRequestCreator.createRequest(
            jobId, 2, Resource.newInstance(1024, 1),
        new String[] {"h2"});
    allocator.sendRequest(event2);

    // this tells the scheduler about the requests
    // as nodes are not added, no allocations
    List<TaskAttemptContainerAssignedEvent> assigned = allocator.schedule();
    rm.drainEvents();
    Assert.assertEquals("No of assignments must be 0", 0, assigned.size());

    // update resources in scheduler
    nodeManager1.nodeHeartbeat(true); // Node heartbeat
    nodeManager2.nodeHeartbeat(true); // Node heartbeat
    nodeManager3.nodeHeartbeat(true); // Node heartbeat
    rm.drainEvents();

    assigned = allocator.schedule();
    rm.drainEvents();
    checkAssignments(new ContainerRequestEvent[] {event1, event2},
        assigned, false);
  }

};