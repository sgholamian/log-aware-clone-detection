//,temp,FSAppAttempt.java,316,371,temp,FiCaSchedulerApp.java,168,216
//,3
public class xxx {
  synchronized public RMContainer allocate(NodeType type, FSSchedulerNode node,
      Priority priority, ResourceRequest request,
      Container container) {
    // Update allowed locality level
    NodeType allowed = allowedLocalityLevel.get(priority);
    if (allowed != null) {
      if (allowed.equals(NodeType.OFF_SWITCH) &&
          (type.equals(NodeType.NODE_LOCAL) ||
              type.equals(NodeType.RACK_LOCAL))) {
        this.resetAllowedLocalityLevel(priority, type);
      }
      else if (allowed.equals(NodeType.RACK_LOCAL) &&
          type.equals(NodeType.NODE_LOCAL)) {
        this.resetAllowedLocalityLevel(priority, type);
      }
    }

    // Required sanity check - AM can call 'allocate' to update resource 
    // request without locking the scheduler, hence we need to check
    if (getTotalRequiredResources(priority) <= 0) {
      return null;
    }
    
    // Create RMContainer
    RMContainer rmContainer = new RMContainerImpl(container, 
        getApplicationAttemptId(), node.getNodeID(),
        appSchedulingInfo.getUser(), rmContext);

    // Add it to allContainers list.
    newlyAllocatedContainers.add(rmContainer);
    liveContainers.put(container.getId(), rmContainer);    

    // Update consumption and track allocations
    List<ResourceRequest> resourceRequestList = appSchedulingInfo.allocate(
        type, node, priority, request, container);
    this.attemptResourceUsage.incUsed(container.getResource());

    // Update resource requests related to "request" and store in RMContainer
    ((RMContainerImpl) rmContainer).setResourceRequests(resourceRequestList);

    // Inform the container
    rmContainer.handle(
        new RMContainerEvent(container.getId(), RMContainerEventType.START));

    if (LOG.isDebugEnabled()) {
      LOG.debug("allocate: applicationAttemptId=" 
          + container.getId().getApplicationAttemptId() 
          + " container=" + container.getId() + " host="
          + container.getNodeId().getHost() + " type=" + type);
    }
    RMAuditLogger.logSuccess(getUser(), 
        AuditConstants.ALLOC_CONTAINER, "SchedulerApp", 
        getApplicationId(), container.getId());
    
    return rmContainer;
  }

};