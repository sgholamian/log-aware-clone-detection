//,temp,TestRMAppTransitions.java,796,812,temp,TestRMAppTransitions.java,761,777
//,2
public class xxx {
  @Test (timeout = 30000)
  public void testAppSubmittedRejected() throws IOException {
    LOG.info("--- START: testAppSubmittedRejected ---");

    RMApp application = testCreateAppSubmittedNoRecovery(null);
    // SUBMITTED => FAILED event RMAppEventType.APP_REJECTED
    String rejectedText = "app rejected";
    RMAppEvent event = new RMAppEvent(application.getApplicationId(),
        RMAppEventType.APP_REJECTED, rejectedText);
    application.handle(event);
    rmDispatcher.await();
    sendAppUpdateSavedEvent(application);
    assertFailed(application, rejectedText);
    assertAppFinalStateSaved(application);
    verifyApplicationFinished(RMAppState.FAILED);
    verifyRMAppFieldsForFinalTransitions(application);
  }

};