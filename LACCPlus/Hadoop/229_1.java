//,temp,TestRMAppTransitions.java,631,646,temp,TestRMAppTransitions.java,580,596
//,3
public class xxx {
  @Test (timeout = 30000)
  public void testAppSubmittedRejected() throws IOException {
    LOG.info("--- START: testAppSubmittedRejected ---");

    RMApp application = testCreateAppSubmittedNoRecovery(null);
    // SUBMITTED => FAILED event RMAppEventType.APP_REJECTED
    String rejectedText = "app rejected";
    RMAppEvent event = 
        new RMAppRejectedEvent(application.getApplicationId(), rejectedText);
    application.handle(event);
    rmDispatcher.await();
    sendAppUpdateSavedEvent(application);
    assertFailed(application, rejectedText);
    assertAppFinalStateSaved(application);
    verifyApplicationFinished(RMAppState.FAILED);
  }

};