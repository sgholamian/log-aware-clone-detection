//,temp,MockRM.java,163,190,temp,MockAM.java,76,101
//,3
public class xxx {
  public void waitForState(RMAppAttemptState finalState) throws Exception {
    RMApp app = context.getRMApps().get(attemptId.getApplicationId());
    RMAppAttempt attempt = app.getRMAppAttempt(attemptId);
    final int timeoutMsecs = 40000;
    final int minWaitMsecs = 1000;
    final int waitMsPerLoop = 500;
    int loop = 0;
    while (!finalState.equals(attempt.getAppAttemptState())
        && waitMsPerLoop * loop < timeoutMsecs) {
      LOG.info("AppAttempt : " + attemptId + " State is : " +
          attempt.getAppAttemptState() + " Waiting for state : " +
          finalState);
      Thread.yield();
      Thread.sleep(waitMsPerLoop);
      loop++;
    }
    int waitedMsecs = waitMsPerLoop * loop;
    if (minWaitMsecs > waitedMsecs) {
      Thread.sleep(minWaitMsecs - waitedMsecs);
    }
    LOG.info("Attempt State is : " + attempt.getAppAttemptState());
    if (waitedMsecs >= timeoutMsecs) {
      Assert.fail("Attempt state is not correct (timedout): expected: "
          + finalState + " actual: " + attempt.getAppAttemptState());
    }
  }

};