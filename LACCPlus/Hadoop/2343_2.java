//,temp,RMContainerAllocator.java,474,516,temp,RMContainerAllocator.java,421,472
//,3
public class xxx {
  @SuppressWarnings({ "unchecked" })
  private void handleReduceContainerRequest(ContainerRequestEvent reqEvent) {
    assert(reqEvent.getAttemptID().getTaskId().getTaskType().equals(
        TaskType.REDUCE));

    Resource supportedMaxContainerCapability = getMaxContainerCapability();
    JobId jobId = getJob().getID();

    if (reduceResourceRequest.equals(Resources.none())) {
      reduceResourceRequest = reqEvent.getCapability();
      eventHandler.handle(new JobHistoryEvent(jobId,
          new NormalizedResourceEvent(
              org.apache.hadoop.mapreduce.TaskType.REDUCE,
              reduceResourceRequest.getMemorySize())));
      LOG.info("reduceResourceRequest:" + reduceResourceRequest);
    }

    boolean reduceContainerRequestAccepted = true;
    if (reduceResourceRequest.getMemorySize() >
        supportedMaxContainerCapability.getMemorySize()
        ||
        reduceResourceRequest.getVirtualCores() >
        supportedMaxContainerCapability.getVirtualCores()) {
      reduceContainerRequestAccepted = false;
    }

    if (reduceContainerRequestAccepted) {
      // set the resources
      reqEvent.getCapability().setVirtualCores(
          reduceResourceRequest.getVirtualCores());
      reqEvent.getCapability().setMemorySize(
          reduceResourceRequest.getMemorySize());

      if (reqEvent.getEarlierAttemptFailed()) {
        //previously failed reducers are added to the front for fail fast
        pendingReduces.addFirst(new ContainerRequest(reqEvent,
            PRIORITY_REDUCE, reduceNodeLabelExpression));
      } else {
        //reduces are added to pending queue and are slowly ramped up
        pendingReduces.add(new ContainerRequest(reqEvent,
            PRIORITY_REDUCE, reduceNodeLabelExpression));
      }
    } else {
      String diagMsg = "REDUCE capability required is more than the " +
          "supported max container capability in the cluster. Killing" +
          " the Job. reduceResourceRequest: " + reduceResourceRequest +
          " maxContainerCapability:" + supportedMaxContainerCapability;
      LOG.info(diagMsg);
      eventHandler.handle(new JobDiagnosticsUpdateEvent(jobId, diagMsg));
      eventHandler.handle(new JobEvent(jobId, JobEventType.JOB_KILL));
    }
  }

};