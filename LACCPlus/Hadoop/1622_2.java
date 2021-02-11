//,temp,TestApplicationCleanup.java,81,158,temp,TestRM.java,145,201
//,3
public class xxx {
  @Test (timeout = 30000)
  public void testAppOnMultiNode() throws Exception {
    Logger rootLogger = LogManager.getRootLogger();
    rootLogger.setLevel(Level.DEBUG);
    conf.set("yarn.scheduler.capacity.node-locality-delay", "-1");
    MockRM rm = new MockRM(conf);
    rm.start();
    MockNM nm1 = rm.registerNode("h1:1234", 5120);
    MockNM nm2 = rm.registerNode("h2:5678", 10240);
    
    RMApp app = rm.submitApp(2000);

    //kick the scheduling
    nm1.nodeHeartbeat(true);

    RMAppAttempt attempt = app.getCurrentAppAttempt();
    MockAM am = rm.sendAMLaunched(attempt.getAppAttemptId());
    am.registerAppAttempt();
    
    //request for containers
    int request = 13;
    am.allocate("h1" , 1000, request, new ArrayList<ContainerId>());
    
    //kick the scheduler
    List<Container> conts = am.allocate(new ArrayList<ResourceRequest>(),
        new ArrayList<ContainerId>()).getAllocatedContainers();
    int contReceived = conts.size();
    while (contReceived < 3) {//only 3 containers are available on node1
      nm1.nodeHeartbeat(true);
      conts.addAll(am.allocate(new ArrayList<ResourceRequest>(),
          new ArrayList<ContainerId>()).getAllocatedContainers());
      contReceived = conts.size();
      LOG.info("Got " + contReceived + " containers. Waiting to get " + 3);
      Thread.sleep(WAIT_SLEEP_MS);
    }
    Assert.assertEquals(3, conts.size());

    //send node2 heartbeat
    conts = am.allocate(new ArrayList<ResourceRequest>(),
        new ArrayList<ContainerId>()).getAllocatedContainers();
    contReceived = conts.size();
    while (contReceived < 10) {
      nm2.nodeHeartbeat(true);
      conts.addAll(am.allocate(new ArrayList<ResourceRequest>(),
          new ArrayList<ContainerId>()).getAllocatedContainers());
      contReceived = conts.size();
      LOG.info("Got " + contReceived + " containers. Waiting to get " + 10);
      Thread.sleep(WAIT_SLEEP_MS);
    }
    Assert.assertEquals(10, conts.size());

    am.unregisterAppAttempt();
    nm1.nodeHeartbeat(attempt.getAppAttemptId(), 1, ContainerState.COMPLETE);
    am.waitForState(RMAppAttemptState.FINISHED);

    rm.stop();
  }

};