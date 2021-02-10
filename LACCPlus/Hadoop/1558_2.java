//,temp,FifoScheduler.java,869,911,temp,FairScheduler.java,808,845
//,3
public class xxx {
  @Override
  protected synchronized void completedContainer(RMContainer rmContainer,
      ContainerStatus containerStatus, RMContainerEventType event) {
    if (rmContainer == null) {
      LOG.info("Container " + containerStatus.getContainerId()
          + " completed with event " + event);
      return;
    }

    Container container = rmContainer.getContainer();

    // Get the application for the finished container
    FSAppAttempt application =
        getCurrentAttemptForContainer(container.getId());
    ApplicationId appId =
        container.getId().getApplicationAttemptId().getApplicationId();
    if (application == null) {
      LOG.info("Container " + container + " of" +
          " finished application " + appId +
          " completed with event " + event);
      return;
    }

    // Get the node on which the container was allocated
    FSSchedulerNode node = getFSSchedulerNode(container.getNodeId());

    if (rmContainer.getState() == RMContainerState.RESERVED) {
      application.unreserve(rmContainer.getReservedPriority(), node);
    } else {
      application.containerCompleted(rmContainer, containerStatus, event);
      node.releaseContainer(container);
      updateRootQueueMetrics();
    }

    LOG.info("Application attempt " + application.getApplicationAttemptId()
        + " released container " + container.getId() + " on node: " + node
        + " with event: " + event);
  }

};