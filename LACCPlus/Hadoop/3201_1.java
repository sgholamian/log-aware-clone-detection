//,temp,FifoScheduler.java,408,440,temp,FairScheduler.java,551,596
//,3
public class xxx {
  @VisibleForTesting
  public synchronized void
      addApplicationAttempt(ApplicationAttemptId appAttemptId,
          boolean transferStateFromPreviousAttempt,
          boolean isAttemptRecovering) {
    SchedulerApplication<FifoAppAttempt> application =
        applications.get(appAttemptId.getApplicationId());
    String user = application.getUser();
    // TODO: Fix store
    FifoAppAttempt schedulerApp =
        new FifoAppAttempt(appAttemptId, user, DEFAULT_QUEUE,
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