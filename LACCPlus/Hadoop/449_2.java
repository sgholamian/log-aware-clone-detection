//,temp,FairScheduler.java,865,902,temp,CapacityScheduler.java,1407,1451
//,3
public class xxx {
  private synchronized void removeNode(RMNode nodeInfo) {
    // update this node to node label manager
    if (labelManager != null) {
      labelManager.deactivateNode(nodeInfo.getNodeID());
    }
    
    FiCaSchedulerNode node = nodes.get(nodeInfo.getNodeID());
    if (node == null) {
      return;
    }
    Resources.subtractFrom(clusterResource, node.getRMNode().getTotalCapability());
    root.updateClusterResource(clusterResource, new ResourceLimits(
        clusterResource));
    int numNodes = numNodeManagers.decrementAndGet();

    if (scheduleAsynchronously && numNodes == 0) {
      asyncSchedulerThread.suspendSchedule();
    }
    
    // Remove running containers
    List<RMContainer> runningContainers = node.getRunningContainers();
    for (RMContainer container : runningContainers) {
      completedContainer(container, 
          SchedulerUtils.createAbnormalContainerStatus(
              container.getContainerId(), 
              SchedulerUtils.LOST_CONTAINER), 
          RMContainerEventType.KILL);
    }
    
    // Remove reservations, if any
    RMContainer reservedContainer = node.getReservedContainer();
    if (reservedContainer != null) {
      completedContainer(reservedContainer, 
          SchedulerUtils.createAbnormalContainerStatus(
              reservedContainer.getContainerId(), 
              SchedulerUtils.LOST_CONTAINER), 
          RMContainerEventType.KILL);
    }

    this.nodes.remove(nodeInfo.getNodeID());
    updateMaximumAllocation(node, false);

    LOG.info("Removed node " + nodeInfo.getNodeAddress() + 
        " clusterResource: " + clusterResource);
  }

};