//,temp,LeafQueue.java,2032,2049,temp,LeafQueue.java,2013,2030
//,2
public class xxx {
  @Override
  public void detachContainer(Resource clusterResource,
      FiCaSchedulerApp application, RMContainer rmContainer) {
    if (application != null && rmContainer != null
          && rmContainer.getExecutionType() == ExecutionType.GUARANTEED) {
      FiCaSchedulerNode node =
          scheduler.getNode(rmContainer.getContainer().getNodeId());
      releaseResource(clusterResource, application, rmContainer.getContainer()
          .getResource(), node.getPartition(), rmContainer);
      LOG.info("movedContainer" + " container=" + rmContainer.getContainer()
          + " resource=" + rmContainer.getContainer().getResource()
          + " queueMoveOut=" + this + " usedCapacity=" + getUsedCapacity()
          + " absoluteUsedCapacity=" + getAbsoluteUsedCapacity() + " used="
          + queueUsage.getUsed() + " cluster=" + clusterResource);
      // Inform the parent queue
      getParent().detachContainer(clusterResource, application, rmContainer);
    }
  }

};