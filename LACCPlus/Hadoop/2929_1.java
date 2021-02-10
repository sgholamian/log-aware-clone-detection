//,temp,TestRMAppTransitions.java,720,737,temp,TestRMAppTransitions.java,702,718
//,3
public class xxx {
  @Test (timeout = 30000)
  public void testAppNewRejectAddToStore() throws IOException {
    LOG.info("--- START: testAppNewRejectAddToStore ---");

    RMApp application = createNewTestApp(null);
    // NEW => FAILED event RMAppEventType.APP_REJECTED
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
    rmContext.getStateStore().removeApplication(application);
  }

};