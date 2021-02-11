//,temp,ParentQueue.java,743,761,temp,LeafQueue.java,1407,1423
//,3
public class xxx {
  @Override
  public void detachContainer(Resource clusterResource,
      FiCaSchedulerApp application, RMContainer rmContainer) {
    if (application != null) {
      FiCaSchedulerNode node =
          scheduler.getNode(rmContainer.getContainer().getNodeId());
      super.releaseResource(clusterResource,
          rmContainer.getContainer().getResource(),
          node.getPartition());
      LOG.info("movedContainer" + " queueMoveOut=" + getQueueName()
          + " usedCapacity=" + getUsedCapacity() + " absoluteUsedCapacity="
          + getAbsoluteUsedCapacity() + " used=" + queueUsage.getUsed() + " cluster="
          + clusterResource);
      // Inform the parent
      if (parent != null) {
        parent.detachContainer(clusterResource, application, rmContainer);
      }
    }
  }

};