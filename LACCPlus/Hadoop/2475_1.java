//,temp,TestFifoScheduler.java,1103,1195,temp,TestCapacityScheduler.java,1233,1340
//,3
public class xxx {
  @Test(timeout = 60000)
  public void testResourceOverCommit() throws Exception {
    int waitCount;
    MockRM rm = new MockRM(conf);
    rm.start();

    MockNM nm1 = rm.registerNode("127.0.0.1:1234", 4 * GB);

    RMApp app1 = rm.submitApp(2048);
    // kick the scheduling, 2 GB given to AM1, remaining 2GB on nm1
    nm1.nodeHeartbeat(true);
    RMAppAttempt attempt1 = app1.getCurrentAppAttempt();
    MockAM am1 = rm.sendAMLaunched(attempt1.getAppAttemptId());
    am1.registerAppAttempt();
    SchedulerNodeReport report_nm1 =
        rm.getResourceScheduler().getNodeReport(nm1.getNodeId());
    // check node report, 2 GB used and 2 GB available
    Assert.assertEquals(2 * GB, report_nm1.getUsedResource().getMemorySize());
    Assert.assertEquals(2 * GB, report_nm1.getAvailableResource().getMemorySize());

    // add request for containers
    am1.addRequests(new String[] { "127.0.0.1", "127.0.0.2" }, 2 * GB, 1, 1);
    AllocateResponse alloc1Response = am1.schedule(); // send the request

    // kick the scheduler, 2 GB given to AM1, resource remaining 0
    nm1.nodeHeartbeat(true);
    while (alloc1Response.getAllocatedContainers().size() < 1) {
      LOG.info("Waiting for containers to be created for app 1...");
      Thread.sleep(1000);
      alloc1Response = am1.schedule();
    }

    List<Container> allocated1 = alloc1Response.getAllocatedContainers();
    Assert.assertEquals(1, allocated1.size());
    Assert.assertEquals(2 * GB, allocated1.get(0).getResource().getMemorySize());
    Assert.assertEquals(nm1.getNodeId(), allocated1.get(0).getNodeId());

    report_nm1 = rm.getResourceScheduler().getNodeReport(nm1.getNodeId());
    // check node report, 4 GB used and 0 GB available
    Assert.assertEquals(0, report_nm1.getAvailableResource().getMemorySize());
    Assert.assertEquals(4 * GB, report_nm1.getUsedResource().getMemorySize());

    // check container is assigned with 2 GB.
    Container c1 = allocated1.get(0);
    Assert.assertEquals(2 * GB, c1.getResource().getMemorySize());

    // update node resource to 2 GB, so resource is over-consumed.
    Map<NodeId, ResourceOption> nodeResourceMap =
        new HashMap<NodeId, ResourceOption>();
    nodeResourceMap.put(nm1.getNodeId(),
        ResourceOption.newInstance(Resource.newInstance(2 * GB, 1), -1));
    UpdateNodeResourceRequest request =
        UpdateNodeResourceRequest.newInstance(nodeResourceMap);
    rm.getAdminService().updateNodeResource(request);

    waitCount = 0;
    while (waitCount++ != 20) {
      report_nm1 = rm.getResourceScheduler().getNodeReport(nm1.getNodeId());
      if (null != report_nm1 &&
          report_nm1.getAvailableResource().getMemorySize() != 0) {
        break;
      }
      LOG.info("Waiting for RMNodeResourceUpdateEvent to be handled... Tried "
          + waitCount + " times already..");
      Thread.sleep(1000);
    }
    // Now, the used resource is still 4 GB, and available resource is minus
    // value.
    report_nm1 = rm.getResourceScheduler().getNodeReport(nm1.getNodeId());
    Assert.assertEquals(4 * GB, report_nm1.getUsedResource().getMemorySize());
    Assert.assertEquals(-2 * GB, report_nm1.getAvailableResource().getMemorySize());

    // Check container can complete successfully in case of resource
    // over-commitment.
    ContainerStatus containerStatus =
        BuilderUtils.newContainerStatus(c1.getId(), ContainerState.COMPLETE,
            "", 0, c1.getResource());
    nm1.containerStatus(containerStatus);
    waitCount = 0;
    while (attempt1.getJustFinishedContainers().size() < 1 && waitCount++ != 20) {
      LOG.info("Waiting for containers to be finished for app 1... Tried "
          + waitCount + " times already..");
      Thread.sleep(100);
    }
    Assert.assertEquals(1, attempt1.getJustFinishedContainers().size());
    Assert.assertEquals(1, am1.schedule().getCompletedContainersStatuses()
        .size());
    report_nm1 = rm.getResourceScheduler().getNodeReport(nm1.getNodeId());
    Assert.assertEquals(2 * GB, report_nm1.getUsedResource().getMemorySize());
    // As container return 2 GB back, the available resource becomes 0 again.
    Assert.assertEquals(0 * GB, report_nm1.getAvailableResource().getMemorySize());
    rm.stop();
  }

};