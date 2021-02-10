//,temp,TestFifoScheduler.java,1081,1161,temp,TestFifoScheduler.java,677,765
//,3
public class xxx {
  @Test(timeout = 60000)
  public void testFifoScheduling() throws Exception {
    Logger rootLogger = LogManager.getRootLogger();
    rootLogger.setLevel(Level.DEBUG);
    MockRM rm = new MockRM(conf);
    rm.start();
    MockNM nm1 = rm.registerNode("127.0.0.1:1234", 6 * GB);
    MockNM nm2 = rm.registerNode("127.0.0.2:5678", 4 * GB);

    RMApp app1 = rm.submitApp(2048);
    // kick the scheduling, 2 GB given to AM1, remaining 4GB on nm1
    nm1.nodeHeartbeat(true);
    RMAppAttempt attempt1 = app1.getCurrentAppAttempt();
    MockAM am1 = rm.sendAMLaunched(attempt1.getAppAttemptId());
    am1.registerAppAttempt();
    SchedulerNodeReport report_nm1 =
        rm.getResourceScheduler().getNodeReport(nm1.getNodeId());
    Assert.assertEquals(2 * GB, report_nm1.getUsedResource().getMemory());

    RMApp app2 = rm.submitApp(2048);
    // kick the scheduling, 2GB given to AM, remaining 2 GB on nm2
    nm2.nodeHeartbeat(true);
    RMAppAttempt attempt2 = app2.getCurrentAppAttempt();
    MockAM am2 = rm.sendAMLaunched(attempt2.getAppAttemptId());
    am2.registerAppAttempt();
    SchedulerNodeReport report_nm2 =
        rm.getResourceScheduler().getNodeReport(nm2.getNodeId());
    Assert.assertEquals(2 * GB, report_nm2.getUsedResource().getMemory());

    // add request for containers
    am1.addRequests(new String[] { "127.0.0.1", "127.0.0.2" }, GB, 1, 1);
    AllocateResponse alloc1Response = am1.schedule(); // send the request
    // add request for containers
    am2.addRequests(new String[] { "127.0.0.1", "127.0.0.2" }, 3 * GB, 0, 1);
    AllocateResponse alloc2Response = am2.schedule(); // send the request

    // kick the scheduler, 1 GB and 3 GB given to AM1 and AM2, remaining 0
    nm1.nodeHeartbeat(true);
    while (alloc1Response.getAllocatedContainers().size() < 1) {
      LOG.info("Waiting for containers to be created for app 1...");
      Thread.sleep(1000);
      alloc1Response = am1.schedule();
    }
    while (alloc2Response.getAllocatedContainers().size() < 1) {
      LOG.info("Waiting for containers to be created for app 2...");
      Thread.sleep(1000);
      alloc2Response = am2.schedule();
    }
    // kick the scheduler, nothing given remaining 2 GB.
    nm2.nodeHeartbeat(true);

    List<Container> allocated1 = alloc1Response.getAllocatedContainers();
    Assert.assertEquals(1, allocated1.size());
    Assert.assertEquals(1 * GB, allocated1.get(0).getResource().getMemory());
    Assert.assertEquals(nm1.getNodeId(), allocated1.get(0).getNodeId());

    List<Container> allocated2 = alloc2Response.getAllocatedContainers();
    Assert.assertEquals(1, allocated2.size());
    Assert.assertEquals(3 * GB, allocated2.get(0).getResource().getMemory());
    Assert.assertEquals(nm1.getNodeId(), allocated2.get(0).getNodeId());

    report_nm1 = rm.getResourceScheduler().getNodeReport(nm1.getNodeId());
    report_nm2 = rm.getResourceScheduler().getNodeReport(nm2.getNodeId());
    Assert.assertEquals(0, report_nm1.getAvailableResource().getMemory());
    Assert.assertEquals(2 * GB, report_nm2.getAvailableResource().getMemory());

    Assert.assertEquals(6 * GB, report_nm1.getUsedResource().getMemory());
    Assert.assertEquals(2 * GB, report_nm2.getUsedResource().getMemory());

    Container c1 = allocated1.get(0);
    Assert.assertEquals(GB, c1.getResource().getMemory());
    ContainerStatus containerStatus =
        BuilderUtils.newContainerStatus(c1.getId(), ContainerState.COMPLETE,
            "", 0);
    nm1.containerStatus(containerStatus);
    int waitCount = 0;
    while (attempt1.getJustFinishedContainers().size() < 1 && waitCount++ != 20) {
      LOG.info("Waiting for containers to be finished for app 1... Tried "
          + waitCount + " times already..");
      Thread.sleep(1000);
    }
    Assert.assertEquals(1, attempt1.getJustFinishedContainers().size());
    Assert.assertEquals(1, am1.schedule().getCompletedContainersStatuses()
        .size());
    report_nm1 = rm.getResourceScheduler().getNodeReport(nm1.getNodeId());
    Assert.assertEquals(5 * GB, report_nm1.getUsedResource().getMemory());

    rm.stop();
  }

};