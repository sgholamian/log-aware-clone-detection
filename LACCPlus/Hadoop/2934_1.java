//,temp,TestRMAppTransitions.java,901,922,temp,TestRMAppTransitions.java,680,700
//,3
public class xxx {
  @Test
  public void testAppAcceptedAttemptKilled() throws IOException,
      InterruptedException {
    LOG.info("--- START: testAppAcceptedAttemptKilled ---");
    RMApp application = testCreateAppAccepted(null);

    // ACCEPTED => FINAL_SAVING event RMAppEventType.ATTEMPT_KILLED
    // When application recovery happens for attempt is KILLED but app is
    // RUNNING.
    RMAppEvent event =
        new RMAppEvent(application.getApplicationId(),
            RMAppEventType.ATTEMPT_KILLED, "Application killed by user.");
    application.handle(event);
    rmDispatcher.await();

    assertAppState(RMAppState.FINAL_SAVING, application);
    sendAppUpdateSavedEvent(application);
    assertKilled(application);
    assertAppFinalStateSaved(application);
    verifyApplicationFinished(RMAppState.KILLED);
    verifyAppRemovedSchedulerEvent(RMAppState.KILLED);
  }

};