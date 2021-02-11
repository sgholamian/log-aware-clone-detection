//,temp,TestRMAppTransitions.java,924,947,temp,TestRMAppTransitions.java,814,835
//,3
public class xxx {
  @Test
  public void testAppRunningKill() throws IOException {
    LOG.info("--- START: testAppRunningKill ---");

    RMApp application = testCreateAppRunning(null);
    // RUNNING => KILLED event RMAppEventType.KILL
    UserGroupInformation fooUser = UserGroupInformation.createUserForTesting(
        "fooTestAppRunningKill", new String[] {"foo_group"});

    // SUBMITTED => KILLED event RMAppEventType.KILL
    RMAppEvent event = new RMAppKillByClientEvent(
        application.getApplicationId(), "Application killed by user.", fooUser,
        Server.getRemoteIp());

    application.handle(event);
    rmDispatcher.await();

    sendAttemptUpdateSavedEvent(application);
    sendAppUpdateSavedEvent(application);
    assertKilled(application);
    verifyApplicationFinished(RMAppState.KILLED);
    verifyAppRemovedSchedulerEvent(RMAppState.KILLED);
    verifyRMAppFieldsForFinalTransitions(application);
  }

};