//,temp,TestRMContainerAllocator.java,1637,1774,temp,TestRMContainerAllocator.java,1338,1446
//,3
public class xxx {
  @Test
  public void testBlackListedNodes() throws Exception {

    LOG.info("Running testBlackListedNodes");

    Configuration conf = new Configuration();
    conf.setBoolean(MRJobConfig.MR_AM_JOB_NODE_BLACKLISTING_ENABLE, true);
    conf.setInt(MRJobConfig.MAX_TASK_FAILURES_PER_TRACKER, 1);
    conf.setInt(
        MRJobConfig.MR_AM_IGNORE_BLACKLISTING_BLACKLISTED_NODE_PERECENT, -1);

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
    ContainerRequestEvent event1 =
            ContainerRequestCreator.createRequest(jobId, 1,
                    Resource.newInstance(1024, 1),
                    new String[] {"h1"});
    allocator.sendRequest(event1);

    // send 1 more request with different resource req
    ContainerRequestEvent event2 =
            ContainerRequestCreator.createRequest(jobId, 2,
                    Resource.newInstance(1024, 1),
                    new String[] {"h2"});
    allocator.sendRequest(event2);

    // send another request with different resource and priority
    ContainerRequestEvent event3 =
            ContainerRequestCreator.createRequest(jobId, 3,
                    Resource.newInstance(1024, 1),
                    new String[] {"h3"});
    allocator.sendRequest(event3);

    // this tells the scheduler about the requests
    // as nodes are not added, no allocations
    List<TaskAttemptContainerAssignedEvent> assigned = allocator.schedule();
    rm.drainEvents();
    Assert.assertEquals("No of assignments must be 0", 0, assigned.size());

    // Send events to blacklist nodes h1 and h2
    ContainerFailedEvent f1 = createFailEvent(jobId, 1, "h1", false);
    allocator.sendFailure(f1);
    ContainerFailedEvent f2 = createFailEvent(jobId, 1, "h2", false);
    allocator.sendFailure(f2);

    // update resources in scheduler
    nodeManager1.nodeHeartbeat(true); // Node heartbeat
    nodeManager2.nodeHeartbeat(true); // Node heartbeat
    rm.drainEvents();

    assigned = allocator.schedule();
    Assert.assertEquals("No of assignments must be 0", 0, assigned.size());
    rm.drainEvents();
    Assert.assertEquals("No of assignments must be 0", 0, assigned.size());
    assertBlacklistAdditionsAndRemovals(2, 0, rm);

    // mark h1/h2 as bad nodes
    nodeManager1.nodeHeartbeat(false);
    nodeManager2.nodeHeartbeat(false);
    rm.drainEvents();

    assigned = allocator.schedule();
    rm.drainEvents();
    assertBlacklistAdditionsAndRemovals(0, 0, rm);
    Assert.assertEquals("No of assignments must be 0", 0, assigned.size());

    nodeManager3.nodeHeartbeat(true); // Node heartbeat
    rm.drainEvents();
    assigned = allocator.schedule();
    rm.drainEvents();
    assertBlacklistAdditionsAndRemovals(0, 0, rm);

    Assert.assertTrue("No of assignments must be 3", assigned.size() == 3);

    // validate that all containers are assigned to h3
    for (TaskAttemptContainerAssignedEvent assig : assigned) {
      Assert.assertTrue("Assigned container host not correct", "h3".equals(assig
          .getContainer().getNodeId().getHost()));
    }
  }

};