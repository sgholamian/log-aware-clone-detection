//,temp,FifoScheduler.java,442,471,temp,CapacityScheduler.java,840,892
//,3
public class xxx {
  private synchronized void doneApplicationAttempt(
      ApplicationAttemptId applicationAttemptId,
      RMAppAttemptState rmAppAttemptFinalState, boolean keepContainers) {
    LOG.info("Application Attempt " + applicationAttemptId + " is done." +
        " finalState=" + rmAppAttemptFinalState);
    
    FiCaSchedulerApp attempt = getApplicationAttempt(applicationAttemptId);
    SchedulerApplication<FiCaSchedulerApp> application =
        applications.get(applicationAttemptId.getApplicationId());

    if (application == null || attempt == null) {
      LOG.info("Unknown application " + applicationAttemptId + " has completed!");
      return;
    }

    // Release all the allocated, acquired, running containers
    for (RMContainer rmContainer : attempt.getLiveContainers()) {
      if (keepContainers
          && rmContainer.getState().equals(RMContainerState.RUNNING)) {
        // do not kill the running container in the case of work-preserving AM
        // restart.
        LOG.info("Skip killing " + rmContainer.getContainerId());
        continue;
      }
      completedContainer(
        rmContainer,
        SchedulerUtils.createAbnormalContainerStatus(
          rmContainer.getContainerId(), SchedulerUtils.COMPLETED_APPLICATION),
        RMContainerEventType.KILL);
    }

    // Release all reserved containers
    for (RMContainer rmContainer : attempt.getReservedContainers()) {
      completedContainer(
        rmContainer,
        SchedulerUtils.createAbnormalContainerStatus(
          rmContainer.getContainerId(), "Application Complete"),
        RMContainerEventType.KILL);
    }

    // Clean up pending requests, metrics etc.
    attempt.stop(rmAppAttemptFinalState);

    // Inform the queue
    String queueName = attempt.getQueue().getQueueName();
    CSQueue queue = queues.get(queueName);
    if (!(queue instanceof LeafQueue)) {
      LOG.error("Cannot finish application " + "from non-leaf queue: "
          + queueName);
    } else {
      queue.finishApplicationAttempt(attempt, queue.getQueueName());
    }
  }

};