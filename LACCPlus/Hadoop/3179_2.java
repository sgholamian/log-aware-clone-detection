//,temp,FairScheduler.java,794,833,temp,CapacityScheduler.java,1917,1967
//,3
public class xxx {
  private void removeNode(RMNode nodeInfo) {
    try {
      writeLock.lock();
      // update this node to node label manager
      if (labelManager != null) {
        labelManager.deactivateNode(nodeInfo.getNodeID());
      }

      NodeId nodeId = nodeInfo.getNodeID();
      FiCaSchedulerNode node = nodeTracker.getNode(nodeId);
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
      getRootQueue().updateClusterResource(clusterResource,
          new ResourceLimits(clusterResource));
      int numNodes = nodeTracker.nodeCount();

      if (scheduleAsynchronously && numNodes == 0) {
        for (AsyncScheduleThread t : asyncSchedulerThreads) {
          t.suspendSchedule();
        }
      }

      LOG.info(
          "Removed node " + nodeInfo.getNodeAddress() + " clusterResource: "
              + getClusterResource());
    } finally {
      writeLock.unlock();
    }
  }

};