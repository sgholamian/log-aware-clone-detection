//,temp,ParentQueue.java,743,761,temp,LeafQueue.java,1389,1405
//,3
public class xxx {
  @Override
  public void attachContainer(Resource clusterResource,
      FiCaSchedulerApp application, RMContainer rmContainer) {
    if (application != null) {
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