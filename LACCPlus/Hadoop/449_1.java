//,temp,FairScheduler.java,865,902,temp,CapacityScheduler.java,1407,1451
//,3
public class xxx {
  private synchronized void removeNode(RMNode rmNode) {
    FSSchedulerNode node = getFSSchedulerNode(rmNode.getNodeID());
    // This can occur when an UNHEALTHY node reconnects
    if (node == null) {
      return;
    }
    Resources.subtractFrom(clusterResource, rmNode.getTotalCapability());
    updateRootQueueMetrics();

    triggerUpdate();

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

    nodes.remove(rmNode.getNodeID());
    queueMgr.getRootQueue().setSteadyFairShare(clusterResource);
    queueMgr.getRootQueue().recomputeSteadyShares();
    updateMaximumAllocation(node, false);
    LOG.info("Removed node " + rmNode.getNodeAddress() +
        " cluster capacity: " + clusterResource);
  }

};