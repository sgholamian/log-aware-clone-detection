//,temp,FSAppAttempt.java,116,152,temp,FiCaSchedulerApp.java,126,166
//,3
public class xxx {
  synchronized public void containerCompleted(RMContainer rmContainer,
      ContainerStatus containerStatus, RMContainerEventType event) {
    
    Container container = rmContainer.getContainer();
    ContainerId containerId = container.getId();
    
    // Remove from the list of newly allocated containers if found
    newlyAllocatedContainers.remove(rmContainer);
    
    // Inform the container
    rmContainer.handle(
        new RMContainerFinishedEvent(
            containerId,
            containerStatus, 
            event)
        );
    LOG.info("Completed container: " + rmContainer.getContainerId() + 
        " in state: " + rmContainer.getState() + " event:" + event);
    
    // Remove from the list of containers
    liveContainers.remove(rmContainer.getContainerId());

    RMAuditLogger.logSuccess(getUser(), 
        AuditConstants.RELEASE_CONTAINER, "SchedulerApp", 
        getApplicationId(), containerId);
    
    // Update usage metrics 
    Resource containerResource = rmContainer.getContainer().getResource();
    queue.getMetrics().releaseResources(getUser(), 1, containerResource);
    this.attemptResourceUsage.decUsed(containerResource);

    // remove from preemption map if it is completed
    preemptionMap.remove(rmContainer);

    // Clear resource utilization metrics cache.
    lastMemoryAggregateAllocationUpdateTime = -1;
  }

};