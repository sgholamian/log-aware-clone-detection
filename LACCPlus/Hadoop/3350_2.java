//,temp,TestRMAppTransitions.java,796,812,temp,TestRMAppTransitions.java,761,777
//,2
public class xxx {
  @Test (timeout = 30000)
  public void testAppNewSavingReject() throws IOException {
    LOG.info("--- START: testAppNewSavingReject ---");

    RMApp application = testCreateAppNewSaving(null);
    // NEW_SAVING => FAILED event RMAppEventType.APP_REJECTED
    String rejectedText = "Test Application Rejected";
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