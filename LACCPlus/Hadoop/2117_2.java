//,temp,TestRMContainerAllocator.java,1107,1211,temp,TestRMContainerAllocator.java,649,725
//,3
public class xxx {
  @Test
  public void testMapReduceScheduling() throws Exception {

    LOG.info("Running testMapReduceScheduling");

    Configuration conf = new Configuration();
    MyResourceManager rm = new MyResourceManager(conf);
    rm.start();
    DrainDispatcher dispatcher = (DrainDispatcher) rm.getRMContext()
        .getDispatcher();

    // Submit the application
    RMApp app = rm.submitApp(1024);
    dispatcher.await();

    MockNM amNodeManager = rm.registerNode("amNM:1234", 2048);
    amNodeManager.nodeHeartbeat(true);
    dispatcher.await();

    ApplicationAttemptId appAttemptId = app.getCurrentAppAttempt()
        .getAppAttemptId();
    rm.sendAMLaunched(appAttemptId);
    dispatcher.await();

    JobId jobId = MRBuilderUtils.newJobId(appAttemptId.getApplicationId(), 0);
    Job mockJob = mock(Job.class);
    when(mockJob.getReport()).thenReturn(
        MRBuilderUtils.newJobReport(jobId, "job", "user", JobState.RUNNING, 0,
            0, 0, 0, 0, 0, 0, "jobfile", null, false, ""));
    MyContainerAllocator allocator = new MyContainerAllocator(rm, conf,
        appAttemptId, mockJob);

    // add resources to scheduler
    MockNM nodeManager1 = rm.registerNode("h1:1234", 1024);
    MockNM nodeManager2 = rm.registerNode("h2:1234", 10240);
    MockNM nodeManager3 = rm.registerNode("h3:1234", 10240);
    dispatcher.await();

    // create the container request
    // send MAP request
    ContainerRequestEvent event1 = createReq(jobId, 1, 2048, new String[] {
        "h1", "h2" }, true, false);
    allocator.sendRequest(event1);

    // send REDUCE request
    ContainerRequestEvent event2 = createReq(jobId, 2, 3000,
        new String[] { "h1" }, false, true);
    allocator.sendRequest(event2);

    // send MAP request
    ContainerRequestEvent event3 = createReq(jobId, 3, 2048,
        new String[] { "h3" }, false, false);
    allocator.sendRequest(event3);

    // this tells the scheduler about the requests
    // as nodes are not added, no allocations
    List<TaskAttemptContainerAssignedEvent> assigned = allocator.schedule();
    dispatcher.await();
    Assert.assertEquals("No of assignments must be 0", 0, assigned.size());

    // update resources in scheduler
    nodeManager1.nodeHeartbeat(true); // Node heartbeat
    nodeManager2.nodeHeartbeat(true); // Node heartbeat
    nodeManager3.nodeHeartbeat(true); // Node heartbeat
    dispatcher.await();

    assigned = allocator.schedule();
    dispatcher.await();
    checkAssignments(new ContainerRequestEvent[] { event1, event3 },
        assigned, false);

    // validate that no container is assigned to h1 as it doesn't have 2048
    for (TaskAttemptContainerAssignedEvent assig : assigned) {
      Assert.assertFalse("Assigned count not correct", "h1".equals(assig
          .getContainer().getNodeId().getHost()));
    }
  }

};