//,temp,TestRMAppTransitions.java,739,759,temp,TestRMAppTransitions.java,680,700
//,3
public class xxx {
  @Test (timeout = 30000)
  public void testAppNewKill() throws IOException {
    LOG.info("--- START: testAppNewKill ---");

    UserGroupInformation fooUser = UserGroupInformation.createUserForTesting(
        "fooTestAppNewKill", new String[] {"foo_group"});

    RMApp application = createNewTestApp(null);
    // NEW => KILLED event RMAppEventType.KILL
    RMAppEvent event = new RMAppKillByClientEvent(
        application.getApplicationId(), "Application killed by user.", fooUser,
        Server.getRemoteIp());
    application.handle(event);
    rmDispatcher.await();
    sendAppUpdateSavedEvent(application);
    assertKilled(application);
    assertAppFinalStateNotSaved(application);
    verifyApplicationFinished(RMAppState.KILLED);
    verifyAppRemovedSchedulerEvent(RMAppState.KILLED);
    verifyRMAppFieldsForFinalTransitions(application);
  }

};