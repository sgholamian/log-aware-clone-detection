//,temp,LeafQueue.java,1182,1210,temp,LeafQueue.java,1145,1180
//,3
public class xxx {
  synchronized void allocateResource(Resource clusterResource,
      SchedulerApplicationAttempt application, Resource resource,
      String nodePartition, RMContainer rmContainer) {
    super.allocateResource(clusterResource, resource, nodePartition);
    
    // handle ignore exclusivity container
    if (null != rmContainer && rmContainer.getNodeLabelExpression().equals(
        RMNodeLabelsManager.NO_LABEL)
        && !nodePartition.equals(RMNodeLabelsManager.NO_LABEL)) {
      TreeSet<RMContainer> rmContainers = null;
      if (null == (rmContainers =
          ignorePartitionExclusivityRMContainers.get(nodePartition))) {
        rmContainers = new TreeSet<>();
        ignorePartitionExclusivityRMContainers.put(nodePartition, rmContainers);
      }
      rmContainers.add(rmContainer);
    }

    // Update user metrics
    String userName = application.getUser();
    User user = getUser(userName);
    user.assignContainer(resource, nodePartition);
    // Note this is a bit unconventional since it gets the object and modifies
    // it here, rather then using set routine
    Resources.subtractFrom(application.getHeadroom(), resource); // headroom
    metrics.setAvailableResourcesToUser(userName, application.getHeadroom());
    
    if (LOG.isDebugEnabled()) {
      LOG.info(getQueueName() + 
          " user=" + userName + 
          " used=" + queueUsage.getUsed() + " numContainers=" + numContainers +
          " headroom = " + application.getHeadroom() +
          " user-resources=" + user.getUsed()
          );
    }
  }

};