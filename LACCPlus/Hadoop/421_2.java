//,temp,LeafQueue.java,1047,1091,temp,AbstractCSQueue.java,447,520
//,3
public class xxx {
  synchronized boolean canAssignToThisQueue(Resource clusterResource,
      String nodePartition, ResourceLimits currentResourceLimits, Resource resourceCouldBeUnreserved,
      SchedulingMode schedulingMode) {
    // Get current limited resource: 
    // - When doing RESPECT_PARTITION_EXCLUSIVITY allocation, we will respect
    // queues' max capacity.
    // - When doing IGNORE_PARTITION_EXCLUSIVITY allocation, we will not respect
    // queue's max capacity, queue's max capacity on the partition will be
    // considered to be 100%. Which is a queue can use all resource in the
    // partition. 
    // Doing this because: for non-exclusive allocation, we make sure there's
    // idle resource on the partition, to avoid wastage, such resource will be
    // leveraged as much as we can, and preemption policy will reclaim it back
    // when partitoned-resource-request comes back.  
    Resource currentLimitResource =
        getCurrentLimitResource(nodePartition, clusterResource,
            currentResourceLimits, schedulingMode);

    Resource nowTotalUsed = queueUsage.getUsed(nodePartition);

    // Set headroom for currentResourceLimits
    currentResourceLimits.setHeadroom(Resources.subtract(currentLimitResource,
        nowTotalUsed));

    if (Resources.greaterThanOrEqual(resourceCalculator, clusterResource,
        nowTotalUsed, currentLimitResource)) {

      // if reservation continous looking enabled, check to see if could we
      // potentially use this node instead of a reserved node if the application
      // has reserved containers.
      // TODO, now only consider reservation cases when the node has no label
      if (this.reservationsContinueLooking
          && nodePartition.equals(RMNodeLabelsManager.NO_LABEL)
          && Resources.greaterThan(resourceCalculator, clusterResource,
              resourceCouldBeUnreserved, Resources.none())) {
        // resource-without-reserved = used - reserved
        Resource newTotalWithoutReservedResource =
            Resources.subtract(nowTotalUsed, resourceCouldBeUnreserved);

        // when total-used-without-reserved-resource < currentLimit, we still
        // have chance to allocate on this node by unreserving some containers
        if (Resources.lessThan(resourceCalculator, clusterResource,
            newTotalWithoutReservedResource, currentLimitResource)) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("try to use reserved: " + getQueueName()
                + " usedResources: " + queueUsage.getUsed()
                + ", clusterResources: " + clusterResource
                + ", reservedResources: " + resourceCouldBeUnreserved
                + ", capacity-without-reserved: "
                + newTotalWithoutReservedResource + ", maxLimitCapacity: "
                + currentLimitResource);
          }
          return true;
        }
      }
      if (LOG.isDebugEnabled()) {
        LOG.debug(getQueueName()
            + "Check assign to queue, nodePartition="
            + nodePartition
            + " usedResources: "
            + queueUsage.getUsed(nodePartition)
            + " clusterResources: "
            + clusterResource
            + " currentUsedCapacity "
            + Resources.divide(resourceCalculator, clusterResource,
                queueUsage.getUsed(nodePartition),
                labelManager.getResourceByLabel(nodePartition, clusterResource))
            + " max-capacity: "
            + queueCapacities.getAbsoluteMaximumCapacity(nodePartition) + ")");
      }
      return false;
    }
    return true;
  }

};