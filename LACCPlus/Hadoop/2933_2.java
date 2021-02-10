//,temp,TestRMAppTransitions.java,924,947,temp,TestRMAppTransitions.java,814,835
//,3
public class xxx {
  @Test
  public void testAppSubmittedKill() throws IOException, InterruptedException {
    LOG.info("--- START: testAppSubmittedKill---");
    RMApp application = testCreateAppSubmittedNoRecovery(null);

    UserGroupInformation fooUser = UserGroupInformation.createUserForTesting(
        "fooTestAppSubmittedKill", new String[] {"foo_group"});

    // SUBMITTED => KILLED event RMAppEventType.KILL
    RMAppEvent event = new RMAppKillByClientEvent(
        application.getApplicationId(), "Application killed by user.", fooUser,
        Server.getRemoteIp());

    application.handle(event);
    rmDispatcher.await();
    sendAppUpdateSavedEvent(application);
    assertKilled(application);
    assertAppFinalStateSaved(application);
    verifyApplicationFinished(RMAppState.KILLED);
    verifyAppRemovedSchedulerEvent(RMAppState.KILLED);
    verifyRMAppFieldsForFinalTransitions(application);
  }

};