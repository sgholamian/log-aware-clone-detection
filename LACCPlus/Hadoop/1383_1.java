//,temp,TestRMAppTransitions.java,699,720,temp,TestRMAppTransitions.java,648,662
//,3
public class xxx {
  @Test
  public void testAppAcceptedKill() throws IOException, InterruptedException {
    LOG.info("--- START: testAppAcceptedKill ---");
    RMApp application = testCreateAppAccepted(null);
    // ACCEPTED => KILLED event RMAppEventType.KILL
    RMAppEvent event = new RMAppEvent(application.getApplicationId(),
        RMAppEventType.KILL);
    application.handle(event);
    rmDispatcher.await();

    assertAppState(RMAppState.KILLING, application);
    RMAppEvent appAttemptKilled =
        new RMAppEvent(application.getApplicationId(),
          RMAppEventType.ATTEMPT_KILLED);
    application.handle(appAttemptKilled);
    assertAppState(RMAppState.FINAL_SAVING, application);
    sendAppUpdateSavedEvent(application);
    assertKilled(application);
    assertAppFinalStateSaved(application);
    verifyApplicationFinished(RMAppState.KILLED);
    verifyAppRemovedSchedulerEvent(RMAppState.KILLED);
  }

};