//,temp,RMContainerAllocator.java,474,516,temp,RMContainerAllocator.java,421,472
//,3
public class xxx {
  @SuppressWarnings({ "unchecked" })
  private void handleMapContainerRequest(ContainerRequestEvent reqEvent) {
    assert(reqEvent.getAttemptID().getTaskId().getTaskType().equals(
        TaskType.MAP));

    Resource supportedMaxContainerCapability = getMaxContainerCapability();
    JobId jobId = getJob().getID();

    if (mapResourceRequest.equals(Resources.none())) {
      mapResourceRequest = reqEvent.getCapability();
      eventHandler.handle(new JobHistoryEvent(jobId,
          new NormalizedResourceEvent(
              org.apache.hadoop.mapreduce.TaskType.MAP,
              mapResourceRequest.getMemorySize())));
      LOG.info("mapResourceRequest:" + mapResourceRequest);
    }

    boolean mapContainerRequestAccepted = true;
    if (mapResourceRequest.getMemorySize() >
        supportedMaxContainerCapability.getMemorySize()
        ||
        mapResourceRequest.getVirtualCores() >
        supportedMaxContainerCapability.getVirtualCores()) {
      mapContainerRequestAccepted = false;
    }

    if(mapContainerRequestAccepted) {
      // set the resources
      reqEvent.getCapability().setMemorySize(
          mapResourceRequest.getMemorySize());
      reqEvent.getCapability().setVirtualCores(
          mapResourceRequest.getVirtualCores());
      scheduledRequests.addMap(reqEvent); //maps are immediately scheduled
    } else {
      String diagMsg = "The required MAP capability is more than the " +
          "supported max container capability in the cluster. Killing" +
          " the Job. mapResourceRequest: " + mapResourceRequest +
          " maxContainerCapability:" + supportedMaxContainerCapability;
      LOG.info(diagMsg);
      eventHandler.handle(new JobDiagnosticsUpdateEvent(jobId, diagMsg));
      eventHandler.handle(new JobEvent(jobId, JobEventType.JOB_KILL));
    }
  }

};