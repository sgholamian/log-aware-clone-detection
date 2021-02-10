//,temp,FairScheduler.java,551,596,temp,CapacityScheduler.java,1000,1053
//,3
public class xxx {
  private void addApplicationAttempt(
      ApplicationAttemptId applicationAttemptId,
      boolean transferStateFromPreviousAttempt,
      boolean isAttemptRecovering) {
    try {
      writeLock.lock();
      SchedulerApplication<FiCaSchedulerApp> application = applications.get(
          applicationAttemptId.getApplicationId());
      if (application == null) {
        LOG.warn("Application " + applicationAttemptId.getApplicationId()
            + " cannot be found in scheduler.");
        return;
      }
      CSQueue queue = (CSQueue) application.getQueue();

      FiCaSchedulerApp attempt = new FiCaSchedulerApp(applicationAttemptId,
          application.getUser(), queue, queue.getAbstractUsersManager(),
          rmContext, application.getPriority(), isAttemptRecovering,
          activitiesManager);
      if (transferStateFromPreviousAttempt) {
        attempt.transferStateFromPreviousAttempt(
            application.getCurrentAppAttempt());
      }
      application.setCurrentAppAttempt(attempt);

      // Update attempt priority to the latest to avoid race condition i.e
      // SchedulerApplicationAttempt is created with old priority but it is not
      // set to SchedulerApplication#setCurrentAppAttempt.
      // Scenario would occur is
      // 1. SchdulerApplicationAttempt is created with old priority.
      // 2. updateApplicationPriority() updates SchedulerApplication. Since
      // currentAttempt is null, it just return.
      // 3. ScheduelerApplcationAttempt is set in
      // SchedulerApplication#setCurrentAppAttempt.
      attempt.setPriority(application.getPriority());

      queue.submitApplicationAttempt(attempt, application.getUser());
      LOG.info("Added Application Attempt " + applicationAttemptId
          + " to scheduler from user " + application.getUser() + " in queue "
          + queue.getQueueName());
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