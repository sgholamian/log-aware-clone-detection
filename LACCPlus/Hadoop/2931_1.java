//,temp,TestRMAppTransitions.java,739,759,temp,TestRMAppTransitions.java,680,700
//,3
public class xxx {
  @Test (timeout = 30000)
  public void testAppNewSavingKill() throws IOException {
    LOG.info("--- START: testAppNewSavingKill ---");

    RMApp application = testCreateAppNewSaving(null);
    // NEW_SAVING => KILLED event RMAppEventType.KILL
    UserGroupInformation fooUser = UserGroupInformation.createUserForTesting(
        "fooTestAppNewSavingKill", new String[] {"foo_group"});

    RMAppEvent event = new RMAppKillByClientEvent(
        application.getApplicationId(), "Application killed by user.", fooUser,
        Server.getRemoteIp());

    application.handle(event);
    rmDispatcher.await();
    sendAppUpdateSavedEvent(application);
    assertKilled(application);
    verifyApplicationFinished(RMAppState.KILLED);
    verifyAppRemovedSchedulerEvent(RMAppState.KILLED);
    verifyRMAppFieldsForFinalTransitions(application);
  }

};