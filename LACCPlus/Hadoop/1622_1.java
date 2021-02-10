//,temp,TestApplicationCleanup.java,81,158,temp,TestRM.java,145,201
//,3
public class xxx {
  @SuppressWarnings("resource")
  @Test
  public void testAppCleanup() throws Exception {
    Logger rootLogger = LogManager.getRootLogger();
    rootLogger.setLevel(Level.DEBUG);
    MockRM rm = new MockRM();
    rm.start();

    MockNM nm1 = rm.registerNode("127.0.0.1:1234", 5000);

    RMApp app = rm.submitApp(2000);

    //kick the scheduling
    nm1.nodeHeartbeat(true);

    RMAppAttempt attempt = app.getCurrentAppAttempt();
    MockAM am = rm.sendAMLaunched(attempt.getAppAttemptId());
    am.registerAppAttempt();
    
    //request for containers
    int request = 2;
    am.allocate("127.0.0.1" , 1000, request, 
        new ArrayList<ContainerId>());
    
    //kick the scheduler
    nm1.nodeHeartbeat(true);
    List<Container> conts = am.allocate(new ArrayList<ResourceRequest>(),
        new ArrayList<ContainerId>()).getAllocatedContainers();
    int contReceived = conts.size();
    int waitCount = 0;
    while (contReceived < request && waitCount++ < 200) {
      LOG.info("Got " + contReceived + " containers. Waiting to get "
               + request);
      Thread.sleep(100);
      conts = am.allocate(new ArrayList<ResourceRequest>(),
          new ArrayList<ContainerId>()).getAllocatedContainers();
      contReceived += conts.size();
      nm1.nodeHeartbeat(true);
    }
    Assert.assertEquals(request, contReceived);
    
    am.unregisterAppAttempt();
    NodeHeartbeatResponse resp = nm1.nodeHeartbeat(attempt.getAppAttemptId(), 1,
        ContainerState.COMPLETE);
    am.waitForState(RMAppAttemptState.FINISHED);

    //currently only containers are cleaned via this
    //AM container is cleaned via container launcher
    resp = nm1.nodeHeartbeat(true);
    List<ContainerId> containersToCleanup = resp.getContainersToCleanup();
    List<ApplicationId> appsToCleanup = resp.getApplicationsToCleanup();
    int numCleanedContainers = containersToCleanup.size();
    int numCleanedApps = appsToCleanup.size();
    waitCount = 0;
    while ((numCleanedContainers < 2 || numCleanedApps < 1)
        && waitCount++ < 200) {
      LOG.info("Waiting to get cleanup events.. cleanedConts: "
          + numCleanedContainers + " cleanedApps: " + numCleanedApps);
      Thread.sleep(100);
      resp = nm1.nodeHeartbeat(true);
      List<ContainerId> deltaContainersToCleanup =
          resp.getContainersToCleanup();
      List<ApplicationId> deltaAppsToCleanup = resp.getApplicationsToCleanup();
      // Add the deltas to the global list
      containersToCleanup.addAll(deltaContainersToCleanup);
      appsToCleanup.addAll(deltaAppsToCleanup);
      // Update counts now
      numCleanedContainers = containersToCleanup.size();
      numCleanedApps = appsToCleanup.size();
    }
    
    Assert.assertEquals(1, appsToCleanup.size());
    Assert.assertEquals(app.getApplicationId(), appsToCleanup.get(0));
    Assert.assertEquals(1, numCleanedApps);
    Assert.assertEquals(2, numCleanedContainers);

    rm.stop();
  }

};