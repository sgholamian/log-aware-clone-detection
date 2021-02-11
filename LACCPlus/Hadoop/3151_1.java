//,temp,TestRMAppTransitions.java,872,899,temp,TestRMAppTransitions.java,680,700
//,3
public class xxx {
  @Test
  public void testAppAcceptedKill() throws IOException, InterruptedException {
    LOG.info("--- START: testAppAcceptedKill ---");
    RMApp application = testCreateAppAccepted(null);
    // ACCEPTED => KILLED event RMAppEventType.KILL
    UserGroupInformation fooUser = UserGroupInformation.createUserForTesting(
        "fooTestAppAcceptedKill", new String[] {"foo_group"});

    RMAppEvent event = new RMAppKillByClientEvent(
        application.getApplicationId(), "Application killed by user.", fooUser,
        Server.getRemoteIp());

    application.handle(event);
    rmDispatcher.await();

    assertAppState(RMAppState.KILLING, application);
    RMAppEvent appAttemptKilled =
        new RMAppEvent(application.getApplicationId(),
          RMAppEventType.ATTEMPT_KILLED, "Application killed by user.");
    application.handle(appAttemptKilled);
    assertAppState(RMAppState.FINAL_SAVING, application);
    sendAppUpdateSavedEvent(application);
    assertKilled(application);
    assertAppFinalStateSaved(application);
    verifyApplicationFinished(RMAppState.KILLED);
    verifyAppRemovedSchedulerEvent(RMAppState.KILLED);
    verifyRMAppFieldsForFinalTransitions(application);
  }

};