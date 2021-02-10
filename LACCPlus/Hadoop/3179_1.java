//,temp,FairScheduler.java,794,833,temp,CapacityScheduler.java,1917,1967
//,3
public class xxx {
  private void removeNode(RMNode rmNode) {
    writeLock.lock();
    try {
      NodeId nodeId = rmNode.getNodeID();
      FSSchedulerNode node = nodeTracker.getNode(nodeId);
      if (node == null) {
        LOG.error("Attempting to remove non-existent node " + nodeId);
        return;
      }

      // Remove running containers
      List<RMContainer> runningContainers =
          node.getCopiedListOfRunningContainers();
      for (RMContainer container : runningContainers) {
        super.completedContainer(container, SchedulerUtils
            .createAbnormalContainerStatus(container.getContainerId(),
                SchedulerUtils.LOST_CONTAINER), RMContainerEventType.KILL);
      }

      // Remove reservations, if any
      RMContainer reservedContainer = node.getReservedContainer();
      if (reservedContainer != null) {
        super.completedContainer(reservedContainer, SchedulerUtils
            .createAbnormalContainerStatus(reservedContainer.getContainerId(),
                SchedulerUtils.LOST_CONTAINER), RMContainerEventType.KILL);
      }

      nodeTracker.removeNode(nodeId);
      Resource clusterResource = getClusterResource();
      queueMgr.getRootQueue().setSteadyFairShare(clusterResource);
      queueMgr.getRootQueue().recomputeSteadyShares();
      updateRootQueueMetrics();
      triggerUpdate();

      LOG.info("Removed node " + rmNode.getNodeAddress() + " cluster capacity: "
          + clusterResource);
    } finally {
      writeLock.unlock();
    }
  }

};