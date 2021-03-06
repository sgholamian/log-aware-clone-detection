//,temp,FairScheduler.java,551,596,temp,CapacityScheduler.java,1000,1053
//,3
public class xxx {
  protected void addApplicationAttempt(
      ApplicationAttemptId applicationAttemptId,
      boolean transferStateFromPreviousAttempt,
      boolean isAttemptRecovering) {
    writeLock.lock();
    try {
      SchedulerApplication<FSAppAttempt> application = applications.get(
          applicationAttemptId.getApplicationId());
      String user = application.getUser();
      FSLeafQueue queue = (FSLeafQueue) application.getQueue();

      FSAppAttempt attempt = new FSAppAttempt(this, applicationAttemptId, user,
          queue, new ActiveUsersManager(getRootQueueMetrics()), rmContext);
      if (transferStateFromPreviousAttempt) {
        attempt.transferStateFromPreviousAttempt(
            application.getCurrentAppAttempt());
      }
      application.setCurrentAppAttempt(attempt);

      boolean runnable = maxRunningEnforcer.canAppBeRunnable(queue, attempt);
      queue.addApp(attempt, runnable);
      if (runnable) {
        maxRunningEnforcer.trackRunnableApp(attempt);
      } else{
        maxRunningEnforcer.trackNonRunnableApp(attempt);
      }

      queue.getMetrics().submitAppAttempt(user);

      LOG.info("Added Application Attempt " + applicationAttemptId
          + " to scheduler from user: " + user);

      if (isAttemptRecovering) {
        if (LOG.isDebugEnabled()) {
          LOG.debug(applicationAttemptId
              + " is recovering. Skipping notifying ATTEMPT_ADDED");
        }
      } else{
        rmContext.getDispatcher().getEventHandler().handle(
            new RMAppAttemptEvent(applicationAttemptId,
                RMAppAttemptEventType.ATTEMPT_ADDED));
      }
    } finally {
      writeLock.unlock();
    }
  }

};