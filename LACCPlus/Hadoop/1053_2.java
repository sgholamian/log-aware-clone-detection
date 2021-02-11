//,temp,FSAppAttempt.java,316,371,temp,FiCaSchedulerApp.java,168,216
//,3
public class xxx {
  synchronized public RMContainer allocate(NodeType type, FiCaSchedulerNode node,
      Priority priority, ResourceRequest request, 
      Container container) {

    if (isStopped) {
      return null;
    }
    
    // Required sanity check - AM can call 'allocate' to update resource 
    // request without locking the scheduler, hence we need to check
    if (getTotalRequiredResources(priority) <= 0) {
      return null;
    }
    
    // Create RMContainer
    RMContainer rmContainer =
        new RMContainerImpl(container, this.getApplicationAttemptId(),
            node.getNodeID(), appSchedulingInfo.getUser(), this.rmContext,
            request.getNodeLabelExpression());

    // Add it to allContainers list.
    newlyAllocatedContainers.add(rmContainer);
    liveContainers.put(container.getId(), rmContainer);    

    // Update consumption and track allocations
    List<ResourceRequest> resourceRequestList = appSchedulingInfo.allocate(
        type, node, priority, request, container);
    attemptResourceUsage.incUsed(node.getPartition(),
        container.getResource());
    
    // Update resource requests related to "request" and store in RMContainer 
    ((RMContainerImpl)rmContainer).setResourceRequests(resourceRequestList);

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