//,temp,MockRM.java,163,190,temp,MockRM.java,140,161
//,3
public class xxx {
  public void waitForState(ApplicationId appId, RMAppState finalState)
      throws Exception {
    RMApp app = getRMContext().getRMApps().get(appId);
    Assert.assertNotNull("app shouldn't be null", app);
    final int timeoutMsecs = 80000;
    final int waitMsPerLoop = 500;
    int loop = 0;
    while (!finalState.equals(app.getState()) &&
        ((waitMsPerLoop * loop) < timeoutMsecs)) {
      LOG.info("App : " + appId + " State is : " + app.getState() +
          " Waiting for state : " + finalState);
      Thread.yield();
      Thread.sleep(waitMsPerLoop);
      loop++;
    }
    int waitedMsecs = waitMsPerLoop * loop;
    LOG.info("App State is : " + app.getState());
    if (waitedMsecs >= timeoutMsecs) {
      Assert.fail("App state is not correct (timedout): expected: " +
          finalState + " actual: " + app.getState());
    }
  }

};