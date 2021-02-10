//,temp,TestRMAppTransitions.java,614,629,temp,TestRMAppTransitions.java,580,596
//,3
public class xxx {
  @Test (timeout = 30000)
  public void testAppNewRejectAddToStore() throws IOException {
    LOG.info("--- START: testAppNewRejectAddToStore ---");

    RMApp application = createNewTestApp(null);
    // NEW => FAILED event RMAppEventType.APP_REJECTED
    String rejectedText = "Test Application Rejected";
    RMAppEvent event =
        new RMAppRejectedEvent(application.getApplicationId(), rejectedText);
    application.handle(event);
    rmDispatcher.await();
    sendAppUpdateSavedEvent(application);
    assertFailed(application, rejectedText);
    assertAppFinalStateSaved(application);
    verifyApplicationFinished(RMAppState.FAILED);
    rmContext.getStateStore().removeApplication(application);
  }

};