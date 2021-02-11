//,temp,FifoScheduler.java,442,471,temp,CapacityScheduler.java,840,892
//,3
public class xxx {
  private synchronized void doneApplicationAttempt(
      ApplicationAttemptId applicationAttemptId,
      RMAppAttemptState rmAppAttemptFinalState, boolean keepContainers)
      throws IOException {
    FiCaSchedulerApp attempt = getApplicationAttempt(applicationAttemptId);
    SchedulerApplication<FiCaSchedulerApp> application =
        applications.get(applicationAttemptId.getApplicationId());
    if (application == null || attempt == null) {
      throw new IOException("Unknown application " + applicationAttemptId + 
      " has completed!");
    }

    // Kill all 'live' containers
    for (RMContainer container : attempt.getLiveContainers()) {
      if (keepContainers
          && container.getState().equals(RMContainerState.RUNNING)) {
        // do not kill the running container in the case of work-preserving AM
        // restart.
        LOG.info("Skip killing " + container.getContainerId());
        continue;
      }
      completedContainer(container,
        SchedulerUtils.createAbnormalContainerStatus(
          container.getContainerId(), SchedulerUtils.COMPLETED_APPLICATION),
        RMContainerEventType.KILL);
    }

    // Clean up pending requests, metrics etc.
    attempt.stop(rmAppAttemptFinalState);
  }

};