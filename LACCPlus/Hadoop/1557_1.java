//,temp,FifoScheduler.java,392,424,temp,FairScheduler.java,653,695
//,3
public class xxx {
  @VisibleForTesting
  public synchronized void
      addApplicationAttempt(ApplicationAttemptId appAttemptId,
          boolean transferStateFromPreviousAttempt,
          boolean isAttemptRecovering) {
    SchedulerApplication<FiCaSchedulerApp> application =
        applications.get(appAttemptId.getApplicationId());
    String user = application.getUser();
    // TODO: Fix store
    FiCaSchedulerApp schedulerApp =
        new FiCaSchedulerApp(appAttemptId, user, DEFAULT_QUEUE,
          activeUsersManager, this.rmContext);

    if (transferStateFromPreviousAttempt) {
      schedulerApp.transferStateFromPreviousAttempt(application
        .getCurrentAppAttempt());
    }
    application.setCurrentAppAttempt(schedulerApp);

    metrics.submitAppAttempt(user);
    LOG.info("Added Application Attempt " + appAttemptId
        + " to scheduler from user " + application.getUser());
    if (isAttemptRecovering) {
      if (LOG.isDebugEnabled()) {
        LOG.debug(appAttemptId
            + " is recovering. Skipping notifying ATTEMPT_ADDED");
      }
    } else {
      rmContext.getDispatcher().getEventHandler().handle(
        new RMAppAttemptEvent(appAttemptId,
            RMAppAttemptEventType.ATTEMPT_ADDED));
    }
  }

};