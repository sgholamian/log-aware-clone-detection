//,temp,TestRMAppTransitions.java,779,794,temp,TestRMAppTransitions.java,761,777
//,3
public class xxx {
  @Test (timeout = 30000)
  public void testAppNewSavingSaveReject() throws IOException {
    LOG.info("--- START: testAppNewSavingSaveReject ---");
    RMApp application = testCreateAppNewSaving(null);
    // NEW_SAVING => FAILED event RMAppEventType.APP_SAVE_FAILED
    String rejectedText = "Test Application Rejected";
    RMAppEvent event = new RMAppEvent(application.getApplicationId(),
        RMAppEventType.APP_SAVE_FAILED, rejectedText);
    application.handle(event);
    rmDispatcher.await();
    assertFailed(application, rejectedText);
    verify(store, times(0)).updateApplicationState(
        any(ApplicationStateData.class));
    verifyApplicationFinished(RMAppState.FAILED);
    assertTimesAtFinish(application);
  }

};