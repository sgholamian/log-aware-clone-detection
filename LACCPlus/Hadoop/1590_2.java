//,temp,FairScheduler.java,653,695,temp,CapacityScheduler.java,786,817
//,3
public class xxx {
  private synchronized void addApplicationAttempt(
      ApplicationAttemptId applicationAttemptId,
      boolean transferStateFromPreviousAttempt,
      boolean isAttemptRecovering) {
    SchedulerApplication<FiCaSchedulerApp> application =
        applications.get(applicationAttemptId.getApplicationId());
    CSQueue queue = (CSQueue) application.getQueue();

    FiCaSchedulerApp attempt = new FiCaSchedulerApp(applicationAttemptId,
        application.getUser(), queue, queue.getActiveUsersManager(), rmContext,
        application.getPriority());
    if (transferStateFromPreviousAttempt) {
      attempt.transferStateFromPreviousAttempt(application
        .getCurrentAppAttempt());
    }
    application.setCurrentAppAttempt(attempt);

    queue.submitApplicationAttempt(attempt, application.getUser());
    LOG.info("Added Application Attempt " + applicationAttemptId
        + " to scheduler from user " + application.getUser() + " in queue "
        + queue.getQueueName());
    if (isAttemptRecovering) {
      if (LOG.isDebugEnabled()) {
        LOG.debug(applicationAttemptId
            + " is recovering. Skipping notifying ATTEMPT_ADDED");
      }
    } else {
      rmContext.getDispatcher().getEventHandler().handle(
        new RMAppAttemptEvent(applicationAttemptId,
            RMAppAttemptEventType.ATTEMPT_ADDED));
    }
  }

};