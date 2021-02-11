//,temp,FifoScheduler.java,869,911,temp,FairScheduler.java,808,845
//,3
public class xxx {
  @Lock(FifoScheduler.class)
  @Override
  protected synchronized void completedContainer(RMContainer rmContainer,
      ContainerStatus containerStatus, RMContainerEventType event) {
    if (rmContainer == null) {
      LOG.info("Null container completed...");
      return;
    }

    // Get the application for the finished container
    Container container = rmContainer.getContainer();
    FiCaSchedulerApp application =
        getCurrentAttemptForContainer(container.getId());
    ApplicationId appId =
        container.getId().getApplicationAttemptId().getApplicationId();
    
    // Get the node on which the container was allocated
    FiCaSchedulerNode node = getNode(container.getNodeId());
    
    if (application == null) {
      LOG.info("Unknown application: " + appId + 
          " released container " + container.getId() +
          " on node: " + node + 
          " with event: " + event);
      return;
    }

    // Inform the application
    application.containerCompleted(rmContainer, containerStatus, event,
        RMNodeLabelsManager.NO_LABEL);

    // Inform the node
    node.releaseContainer(container);
    
    // Update total usage
    Resources.subtractFrom(usedResource, container.getResource());

    LOG.info("Application attempt " + application.getApplicationAttemptId() + 
        " released container " + container.getId() +
        " on node: " + node + 
        " with event: " + event);
     
  }

};