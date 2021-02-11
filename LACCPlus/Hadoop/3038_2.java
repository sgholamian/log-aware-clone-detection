//,temp,ParentQueue.java,1201,1219,temp,LeafQueue.java,2013,2030
//,3
public class xxx {
  @Override
  public void attachContainer(Resource clusterResource,
      FiCaSchedulerApp application, RMContainer rmContainer) {
    if (application != null && rmContainer != null
        && rmContainer.getExecutionType() == ExecutionType.GUARANTEED) {
      FiCaSchedulerNode node =
          scheduler.getNode(rmContainer.getContainer().getNodeId());
      allocateResource(clusterResource, application, rmContainer.getContainer()
          .getResource(), node.getPartition(), rmContainer);
      LOG.info("movedContainer" + " container=" + rmContainer.getContainer()
          + " resource=" + rmContainer.getContainer().getResource()
          + " queueMoveIn=" + this + " usedCapacity=" + getUsedCapacity()
          + " absoluteUsedCapacity=" + getAbsoluteUsedCapacity() + " used="
          + queueUsage.getUsed() + " cluster=" + clusterResource);
      // Inform the parent queue
      getParent().attachContainer(clusterResource, application, rmContainer);
    }
  }

};