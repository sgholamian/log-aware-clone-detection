//,temp,TestRMAppTransitions.java,745,761,temp,TestRMAppTransitions.java,648,662
//,3
public class xxx {
  @Test
  public void testAppSubmittedKill() throws IOException, InterruptedException {
    LOG.info("--- START: testAppSubmittedKill---");
    RMApp application = testCreateAppSubmittedNoRecovery(null);
    // SUBMITTED => KILLED event RMAppEventType.KILL
    RMAppEvent event = new RMAppEvent(application.getApplicationId(),
        RMAppEventType.KILL);
    application.handle(event);
    rmDispatcher.await();
    sendAppUpdateSavedEvent(application);
    assertKilled(application);
    assertAppFinalStateSaved(application);
    verifyApplicationFinished(RMAppState.KILLED);
    verifyAppRemovedSchedulerEvent(RMAppState.KILLED);
  }

};