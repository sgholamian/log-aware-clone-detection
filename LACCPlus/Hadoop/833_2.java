//,temp,TestRMAppTransitions.java,722,743,temp,TestRMAppTransitions.java,546,561
//,3
public class xxx {
  @Test (timeout = 30000)
  public void testAppNewKill() throws IOException {
    LOG.info("--- START: testAppNewKill ---");

    RMApp application = createNewTestApp(null);
    // NEW => KILLED event RMAppEventType.KILL
    RMAppEvent event = 
        new RMAppEvent(application.getApplicationId(), RMAppEventType.KILL);
    application.handle(event);
    rmDispatcher.await();
    sendAppUpdateSavedEvent(application);
    assertKilled(application);
    assertAppFinalStateNotSaved(application);
    verifyApplicationFinished(RMAppState.KILLED);
    verifyAppRemovedSchedulerEvent(RMAppState.KILLED);
  }

};