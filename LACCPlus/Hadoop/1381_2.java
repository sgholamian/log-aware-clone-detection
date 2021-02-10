//,temp,TestRMAppTransitions.java,614,629,temp,TestRMAppTransitions.java,563,578
//,3
public class xxx {
  @Test
  public void testAppNewReject() throws IOException {
    LOG.info("--- START: testAppNewReject ---");

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
  }

};