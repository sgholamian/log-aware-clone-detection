//,temp,CapacityScheduler.java,2114,2143,temp,CapacityScheduler.java,2078,2112
//,3
public class xxx {
  private void markContainerForNonKillable(
      RMContainer nonKillableContainer) {
    try {
      writeLock.lock();
      if (LOG.isDebugEnabled()) {
        LOG.debug(
            SchedulerEventType.MARK_CONTAINER_FOR_NONKILLABLE + ": container"
                + nonKillableContainer.toString());
      }

      FiCaSchedulerNode node = (FiCaSchedulerNode) getSchedulerNode(
          nonKillableContainer.getAllocatedNode());

      FiCaSchedulerApp application = getCurrentAttemptForContainer(
          nonKillableContainer.getContainerId());

      node.markContainerToNonKillable(nonKillableContainer.getContainerId());

      // notify PreemptionManager
      // Get the application for the finished container
      if (null != application) {
        String leafQueueName = application.getCSLeafQueue().getQueueName();
        getPreemptionManager().removeKillableContainer(
            new KillableContainer(nonKillableContainer, node.getPartition(),
                leafQueueName));
      }
    } finally {
      writeLock.unlock();
    }
  }

};