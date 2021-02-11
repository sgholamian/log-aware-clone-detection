//,temp,TestRMAppTransitions.java,745,761,temp,TestRMAppTransitions.java,598,612
//,3
public class xxx {
  @Test
  public void testAppRunningKill() throws IOException {
    LOG.info("--- START: testAppRunningKill ---");

    RMApp application = testCreateAppRunning(null);
    // RUNNING => KILLED event RMAppEventType.KILL
    RMAppEvent event = 
        new RMAppEvent(application.getApplicationId(), RMAppEventType.KILL);
    application.handle(event);
    rmDispatcher.await();

    sendAttemptUpdateSavedEvent(application);
    sendAppUpdateSavedEvent(application);
    assertKilled(application);
    verifyApplicationFinished(RMAppState.KILLED);
    verifyAppRemovedSchedulerEvent(RMAppState.KILLED);
  }

};