//,temp,CapacityScheduler.java,2114,2143,temp,CapacityScheduler.java,2078,2112
//,3
public class xxx {
  public void markContainerForKillable(
      RMContainer killableContainer) {
    try {
      writeLock.lock();
      if (LOG.isDebugEnabled()) {
        LOG.debug(SchedulerEventType.MARK_CONTAINER_FOR_KILLABLE + ": container"
            + killableContainer.toString());
      }

      if (!isLazyPreemptionEnabled) {
        super.completedContainer(killableContainer, SchedulerUtils
            .createPreemptedContainerStatus(killableContainer.getContainerId(),
                SchedulerUtils.PREEMPTED_CONTAINER), RMContainerEventType.KILL);
      } else{
        FiCaSchedulerNode node = (FiCaSchedulerNode) getSchedulerNode(
            killableContainer.getAllocatedNode());

        FiCaSchedulerApp application = getCurrentAttemptForContainer(
            killableContainer.getContainerId());

        node.markContainerToKillable(killableContainer.getContainerId());

        // notify PreemptionManager
        // Get the application for the finished container
        if (null != application) {
          String leafQueueName = application.getCSLeafQueue().getQueueName();
          getPreemptionManager().addKillableContainer(
              new KillableContainer(killableContainer, node.getPartition(),
                  leafQueueName));
        }
      }
    } finally {
      writeLock.unlock();
    }
  }

};