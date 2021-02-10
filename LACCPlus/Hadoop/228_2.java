//,temp,TestRMAppTransitions.java,614,629,temp,TestRMAppTransitions.java,598,612
//,3
public class xxx {
  @Test (timeout = 30000)
  public void testAppNewSavingKill() throws IOException {
    LOG.info("--- START: testAppNewSavingKill ---");

    RMApp application = testCreateAppNewSaving(null);
    // NEW_SAVING => KILLED event RMAppEventType.KILL
    RMAppEvent event =
        new RMAppEvent(application.getApplicationId(), RMAppEventType.KILL);
    application.handle(event);
    rmDispatcher.await();
    sendAppUpdateSavedEvent(application);
    assertKilled(application);
    verifyApplicationFinished(RMAppState.KILLED);
    verifyAppRemovedSchedulerEvent(RMAppState.KILLED);
  }

};