//,temp,LeafQueue.java,1182,1210,temp,LeafQueue.java,1145,1180
//,3
public class xxx {
  synchronized void releaseResource(Resource clusterResource,
      FiCaSchedulerApp application, Resource resource, String nodePartition,
      RMContainer rmContainer) {
    super.releaseResource(clusterResource, resource, nodePartition);
    
    // handle ignore exclusivity container
    if (null != rmContainer && rmContainer.getNodeLabelExpression().equals(
        RMNodeLabelsManager.NO_LABEL)
        && !nodePartition.equals(RMNodeLabelsManager.NO_LABEL)) {
      if (ignorePartitionExclusivityRMContainers.containsKey(nodePartition)) {
        Set<RMContainer> rmContainers =
            ignorePartitionExclusivityRMContainers.get(nodePartition);
        rmContainers.remove(rmContainer);
        if (rmContainers.isEmpty()) {
          ignorePartitionExclusivityRMContainers.remove(nodePartition);
        }
      }
    }

    // Update user metrics
    String userName = application.getUser();
    User user = getUser(userName);
    user.releaseContainer(resource, nodePartition);
    metrics.setAvailableResourcesToUser(userName, application.getHeadroom());
      
    LOG.info(getQueueName() + 
        " used=" + queueUsage.getUsed() + " numContainers=" + numContainers + 
        " user=" + userName + " user-resources=" + user.getUsed());
  }

};