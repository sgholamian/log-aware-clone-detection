//,temp,TestRMContainerAllocator.java,402,453,temp,TestRMContainerAllocator.java,250,334
//,3
public class xxx {
  @Test 
  public void testMapNodeLocality() throws Exception {
    // test checks that ordering of allocated containers list from the RM does 
    // not affect the map->container assignment done by the AM. If there is a 
    // node local container available for a map then it should be assigned to 
    // that container and not a rack-local container that happened to be seen 
    // earlier in the allocated containers list from the RM.
    // Regression test for MAPREDUCE-4893
    LOG.info("Running testMapNodeLocality");

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
    MockNM nodeManager1 = rm.registerNode("h1:1234", 3072); // can assign 2 maps 
    rm.registerNode("h2:1234", 10240); // wont heartbeat on node local node
    MockNM nodeManager3 = rm.registerNode("h3:1234", 1536); // assign 1 map
    dispatcher.await();

    // create the container requests for maps
    ContainerRequestEvent event1 = createReq(jobId, 1, 1024,
        new String[] { "h1" });
    allocator.sendRequest(event1);
    ContainerRequestEvent event2 = createReq(jobId, 2, 1024,
        new String[] { "h1" });
    allocator.sendRequest(event2);
    ContainerRequestEvent event3 = createReq(jobId, 3, 1024,
        new String[] { "h2" });
    allocator.sendRequest(event3);

    // this tells the scheduler about the requests
    // as nodes are not added, no allocations
    List<TaskAttemptContainerAssignedEvent> assigned = allocator.schedule();
    dispatcher.await();
    Assert.assertEquals("No of assignments must be 0", 0, assigned.size());

    // update resources in scheduler
    // Node heartbeat from rack-local first. This makes node h3 the first in the
    // list of allocated containers but it should not be assigned to task1.
    nodeManager3.nodeHeartbeat(true);
    // Node heartbeat from node-local next. This allocates 2 node local 
    // containers for task1 and task2. These should be matched with those tasks.
    nodeManager1.nodeHeartbeat(true);
    dispatcher.await();

    assigned = allocator.schedule();
    dispatcher.await();
    checkAssignments(new ContainerRequestEvent[] { event1, event2, event3 },
        assigned, false);
    // remove the rack-local assignment that should have happened for task3
    for(TaskAttemptContainerAssignedEvent event : assigned) {
      if(event.getTaskAttemptID().equals(event3.getAttemptID())) {
        assigned.remove(event);
        Assert.assertTrue(
                    event.getContainer().getNodeId().getHost().equals("h3"));
        break;
      }
    }
    checkAssignments(new ContainerRequestEvent[] { event1, event2},
        assigned, true);
  }

};