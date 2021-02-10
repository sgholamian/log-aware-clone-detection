//,temp,TestRMContainerAllocator.java,3303,3431,temp,TestRMContainerAllocator.java,3133,3258
//,3
public class xxx {
  @Test
  public void testAvoidAskMoreReducersWhenReducerPreemptionIsRequired()
      throws Exception {
    LOG.info("Running testAvoidAskMoreReducersWhenReducerPreemptionIsRequired");
    Configuration conf = new Configuration();
    MyResourceManager rm = new MyResourceManager(conf);
    rm.start();

    // Submit the application
    RMApp app = rm.submitApp(1024);
    rm.drainEvents();

    MockNM amNodeManager = rm.registerNode("amNM:1234", 1260);
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
    // Use a controlled clock to advance time for test.
    ControlledClock clock = (ControlledClock)allocator.getContext().getClock();
    clock.setTime(System.currentTimeMillis());

    // Register nodes to RM.
    MockNM nodeManager = rm.registerNode("h1:1234", 1024);
    rm.drainEvents();

    // Request 2 maps and 1 reducer(sone on nodes which are not registered).
    ContainerRequestEvent event1 =
            ContainerRequestCreator.createRequest(jobId, 1,
                    Resource.newInstance(1024, 1),
                    new String[]{"h1"});
    allocator.sendRequest(event1);
    ContainerRequestEvent event2 =
            ContainerRequestCreator.createRequest(jobId, 2,
                    Resource.newInstance(1024, 1),
                    new String[]{"h2"});
    allocator.sendRequest(event2);
    ContainerRequestEvent event3 =
            createRequest(jobId, 3, Resource.newInstance(1024, 1),
                    new String[]{"h2"}, false, true);
    allocator.sendRequest(event3);

    // This will tell the scheduler about the requests but there will be no
    // allocations as nodes are not added.
    allocator.schedule();
    rm.drainEvents();

    // Advance clock so that maps can be considered as hanging.
    clock.setTime(System.currentTimeMillis() + 500000L);

    // Request for another reducer on h3 which has not registered.
    ContainerRequestEvent event4 =
            createRequest(jobId, 4, Resource.newInstance(1024, 1),
                    new String[]{"h3"}, false, true);
    allocator.sendRequest(event4);

    allocator.schedule();
    rm.drainEvents();

    // Update resources in scheduler through node heartbeat from h1.
    nodeManager.nodeHeartbeat(true);
    rm.drainEvents();

    rm.getMyFifoScheduler().forceResourceLimit(Resource.newInstance(1024, 1));
    allocator.schedule();
    rm.drainEvents();

    // One map is assigned.
    Assert.assertEquals(1, allocator.getAssignedRequests().maps.size());
    // Send deallocate request for map so that no maps are assigned after this.
    ContainerAllocatorEvent deallocate = createDeallocateEvent(jobId, 1, false);
    allocator.sendDeallocate(deallocate);
    // Now one reducer should be scheduled and one should be pending.
    Assert.assertEquals(1, allocator.getScheduledRequests().reduces.size());
    Assert.assertEquals(1, allocator.getNumOfPendingReduces());
    // No map should be assigned and one should be scheduled.
    Assert.assertEquals(1, allocator.getScheduledRequests().maps.size());
    Assert.assertEquals(0, allocator.getAssignedRequests().maps.size());

    Assert.assertEquals(6, allocator.getAsk().size());
    for (ResourceRequest req : allocator.getAsk()) {
      boolean isReduce =
          req.getPriority().equals(RMContainerAllocator.PRIORITY_REDUCE);
      if (isReduce) {
        // 1 reducer each asked on h2, * and default-rack
        Assert.assertTrue((req.getResourceName().equals("*") ||
            req.getResourceName().equals("/default-rack") ||
            req.getResourceName().equals("h2")) && req.getNumContainers() == 1);
      } else { //map
        // 0 mappers asked on h1 and 1 each on * and default-rack
        Assert.assertTrue(((req.getResourceName().equals("*") ||
            req.getResourceName().equals("/default-rack")) &&
            req.getNumContainers() == 1) || (req.getResourceName().equals("h1")
            && req.getNumContainers() == 0));
      }
    }

    clock.setTime(System.currentTimeMillis() + 500000L + 10 * 60 * 1000);

    // On next allocate request to scheduler, headroom reported will be 2048.
    rm.getMyFifoScheduler().forceResourceLimit(Resource.newInstance(2048, 0));
    allocator.schedule();
    rm.drainEvents();
    // After allocate response from scheduler, all scheduled reduces are ramped
    // down and move to pending. 3 asks are also updated with 0 containers to
    // indicate ramping down of reduces to scheduler.
    Assert.assertEquals(0, allocator.getScheduledRequests().reduces.size());
    Assert.assertEquals(2, allocator.getNumOfPendingReduces());
    Assert.assertEquals(3, allocator.getAsk().size());
    for (ResourceRequest req : allocator.getAsk()) {
      Assert.assertEquals(
          RMContainerAllocator.PRIORITY_REDUCE, req.getPriority());
      Assert.assertTrue(req.getResourceName().equals("*") ||
          req.getResourceName().equals("/default-rack") ||
          req.getResourceName().equals("h2"));
      Assert.assertEquals(Resource.newInstance(1024, 1), req.getCapability());
      Assert.assertEquals(0, req.getNumContainers());
    }
  }

};