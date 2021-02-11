//,temp,TestRMContainerAllocator.java,1637,1774,temp,TestRMContainerAllocator.java,1338,1446
//,3
public class xxx {
  @Test
  public void testBlackListedNodesWithSchedulingToThatNode() throws Exception {
    LOG.info("Running testBlackListedNodesWithSchedulingToThatNode");

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
    MockNM nodeManager3 = rm.registerNode("h3:1234", 10240);
    rm.drainEvents();

    LOG.info("Requesting 1 Containers _1 on H1");
    // create the container request
    ContainerRequestEvent event1 =
            ContainerRequestCreator.createRequest(jobId, 1,
                    Resource.newInstance(1024, 1),
                    new String[] {"h1"});
    allocator.sendRequest(event1);

    LOG.info("RM Heartbeat (to send the container requests)");
    // this tells the scheduler about the requests
    // as nodes are not added, no allocations
    List<TaskAttemptContainerAssignedEvent> assigned = allocator.schedule();
    rm.drainEvents();
    Assert.assertEquals("No of assignments must be 0", 0, assigned.size());

    LOG.info("h1 Heartbeat (To actually schedule the containers)");
    // update resources in scheduler
    nodeManager1.nodeHeartbeat(true); // Node heartbeat
    rm.drainEvents();

    LOG.info("RM Heartbeat (To process the scheduled containers)");
    assigned = allocator.schedule();
    rm.drainEvents();
    assertBlacklistAdditionsAndRemovals(0, 0, rm);
    Assert.assertEquals("No of assignments must be 1", 1, assigned.size());

    LOG.info("Failing container _1 on H1 (should blacklist the node)");
    // Send events to blacklist nodes h1 and h2
    ContainerFailedEvent f1 = createFailEvent(jobId, 1, "h1", false);
    allocator.sendFailure(f1);

    //At this stage, a request should be created for a fast fail map
    //Create a FAST_FAIL request for a previously failed map.
    ContainerRequestEvent event1f = createRequest(jobId, 1,
            Resource.newInstance(1024, 1),
            new String[] {"h1"}, true, false);
    allocator.sendRequest(event1f);

    //Update the Scheduler with the new requests.
    assigned = allocator.schedule();
    rm.drainEvents();
    assertBlacklistAdditionsAndRemovals(1, 0, rm);
    Assert.assertEquals("No of assignments must be 0", 0, assigned.size());

    // send another request with different resource and priority
    ContainerRequestEvent event3 =
            ContainerRequestCreator.createRequest(jobId, 3,
                    Resource.newInstance(1024, 1),
                    new String[] {"h1", "h3"});
    allocator.sendRequest(event3);

    //Allocator is aware of prio:5 container, and prio:20 (h1+h3) container.
    //RM is only aware of the prio:5 container

    LOG.info("h1 Heartbeat (To actually schedule the containers)");
    // update resources in scheduler
    nodeManager1.nodeHeartbeat(true); // Node heartbeat
    rm.drainEvents();

    LOG.info("RM Heartbeat (To process the scheduled containers)");
    assigned = allocator.schedule();
    rm.drainEvents();
    assertBlacklistAdditionsAndRemovals(0, 0, rm);
    Assert.assertEquals("No of assignments must be 0", 0, assigned.size());

    //RMContainerAllocator gets assigned a p:5 on a blacklisted node.

    //Send a release for the p:5 container + another request.
    LOG.info("RM Heartbeat (To process the re-scheduled containers)");
    assigned = allocator.schedule();
    rm.drainEvents();
    assertBlacklistAdditionsAndRemovals(0, 0, rm);
    Assert.assertEquals("No of assignments must be 0", 0, assigned.size());

    //Hearbeat from H3 to schedule on this host.
    LOG.info("h3 Heartbeat (To re-schedule the containers)");
    nodeManager3.nodeHeartbeat(true); // Node heartbeat
    rm.drainEvents();

    LOG.info("RM Heartbeat (To process the re-scheduled containers for H3)");
    assigned = allocator.schedule();
    assertBlacklistAdditionsAndRemovals(0, 0, rm);
    rm.drainEvents();

    // For debugging
    for (TaskAttemptContainerAssignedEvent assig : assigned) {
      LOG.info(assig.getTaskAttemptID() +
          " assgined to " + assig.getContainer().getId() +
          " with priority " + assig.getContainer().getPriority());
    }

    Assert.assertEquals("No of assignments must be 2", 2, assigned.size());

    // validate that all containers are assigned to h3
    for (TaskAttemptContainerAssignedEvent assig : assigned) {
      Assert.assertEquals("Assigned container " + assig.getContainer().getId()
          + " host not correct", "h3", assig.getContainer().getNodeId().getHost());
    }
  }

};