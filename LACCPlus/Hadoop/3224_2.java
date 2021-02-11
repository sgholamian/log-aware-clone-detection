//,temp,TestPlacementProcessor.java,528,613,temp,TestPlacementProcessor.java,372,455
//,3
public class xxx {
  @Test(timeout = 300000)
  public void testSchedulerRejection() throws Exception {
    stopRM();
    CapacitySchedulerConfiguration csConf =
        new CapacitySchedulerConfiguration();
    csConf.setQueues(CapacitySchedulerConfiguration.ROOT,
        new String[] {"a", "b"});
    csConf.setCapacity(CapacitySchedulerConfiguration.ROOT + ".a", 15.0f);
    csConf.setCapacity(CapacitySchedulerConfiguration.ROOT + ".b", 85.0f);
    YarnConfiguration conf = new YarnConfiguration(csConf);
    conf.setClass(YarnConfiguration.RM_SCHEDULER, CapacityScheduler.class,
        ResourceScheduler.class);
    conf.set(YarnConfiguration.RM_PLACEMENT_CONSTRAINTS_HANDLER,
        YarnConfiguration.PROCESSOR_RM_PLACEMENT_CONSTRAINTS_HANDLER);
    startRM(conf);

    HashMap<NodeId, MockNM> nodes = new HashMap<>();
    MockNM nm1 = new MockNM("h1:1234", 4096, rm.getResourceTrackerService());
    nodes.put(nm1.getNodeId(), nm1);
    MockNM nm2 = new MockNM("h2:1234", 4096, rm.getResourceTrackerService());
    nodes.put(nm2.getNodeId(), nm2);
    MockNM nm3 = new MockNM("h3:1234", 4096, rm.getResourceTrackerService());
    nodes.put(nm3.getNodeId(), nm3);
    MockNM nm4 = new MockNM("h4:1234", 4096, rm.getResourceTrackerService());
    nodes.put(nm4.getNodeId(), nm4);
    nm1.registerNode();
    nm2.registerNode();
    nm3.registerNode();
    nm4.registerNode();

    RMApp app1 = rm.submitApp(1 * GB, "app", "user", null, "a");
    // Containers with allocationTag 'foo' are restricted to 1 per NODE
    MockAM am1 = MockRM.launchAndRegisterAM(app1, rm, nm2,
        Collections.singletonMap(
            Collections.singleton("foo"),
            PlacementConstraints.build(
                PlacementConstraints.targetNotIn(NODE, allocationTag("foo")))
        ));
    am1.addSchedulingRequest(
        Arrays.asList(
            schedulingRequest(1, 1, 1, 512, "foo"),
            schedulingRequest(1, 2, 1, 512, "foo"),
            schedulingRequest(1, 3, 1, 512, "foo"),
            // Ask for a container larger than the node
            schedulingRequest(1, 4, 1, 512, "foo"))
    );
    AllocateResponse allocResponse = am1.schedule(); // send the request
    List<Container> allocatedContainers = new ArrayList<>();
    List<RejectedSchedulingRequest> rejectedReqs = new ArrayList<>();
    int allocCount = 1;
    allocatedContainers.addAll(allocResponse.getAllocatedContainers());
    rejectedReqs.addAll(allocResponse.getRejectedSchedulingRequests());

    // kick the scheduler
    while (allocCount < 11) {
      nm1.nodeHeartbeat(true);
      nm2.nodeHeartbeat(true);
      nm3.nodeHeartbeat(true);
      nm4.nodeHeartbeat(true);
      LOG.info("Waiting for containers to be created for app 1...");
      sleep(1000);
      allocResponse = am1.schedule();
      allocatedContainers.addAll(allocResponse.getAllocatedContainers());
      rejectedReqs.addAll(allocResponse.getRejectedSchedulingRequests());
      allocCount++;
      if (rejectedReqs.size() > 0 && allocatedContainers.size() > 2) {
        break;
      }
    }

    Assert.assertEquals(3, allocatedContainers.size());
    Set<NodeId> nodeIds = allocatedContainers.stream()
        .map(x -> x.getNodeId()).collect(Collectors.toSet());
    // Ensure unique nodes
    Assert.assertEquals(3, nodeIds.size());
    RejectedSchedulingRequest rej = rejectedReqs.get(0);
    Assert.assertEquals(4, rej.getRequest().getAllocationRequestId());
    Assert.assertEquals(RejectionReason.COULD_NOT_SCHEDULE_ON_NODE,
        rej.getReason());

    QueueMetrics metrics = rm.getResourceScheduler().getRootQueueMetrics();
    // Verify Metrics
    verifyMetrics(metrics, 12288, 12, 4096, 4, 4);
  }

};