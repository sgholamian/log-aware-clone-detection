//,temp,FirstFitPlanner.java,493,525,temp,FirstFitPlanner.java,458,491
//,3
public class xxx {
    protected Pair<List<Long>, Map<Long, Double>> listClustersByCapacity(long id, int requiredCpu, long requiredRam, ExcludeList avoid, boolean isZone) {
        //look at the aggregate available cpu and ram per cluster
        //although an aggregate value may be false indicator that a cluster can host a vm, it will at the least eliminate those clusters which definitely cannot

        //we need clusters having enough cpu AND RAM to host this particular VM and order them by aggregate cluster capacity
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Listing clusters in order of aggregate capacity, that have (atleast one host with) enough CPU and RAM capacity under this " +
                (isZone ? "Zone: " : "Pod: ") + id);
        }
        String capacityTypeToOrder = configDao.getValue(Config.HostCapacityTypeToOrderClusters.key());
        short capacityType = Capacity.CAPACITY_TYPE_CPU;
        if ("RAM".equalsIgnoreCase(capacityTypeToOrder)) {
            capacityType = Capacity.CAPACITY_TYPE_MEMORY;
        }

        List<Long> clusterIdswithEnoughCapacity = capacityDao.listClustersInZoneOrPodByHostCapacities(id, requiredCpu, requiredRam, capacityType, isZone);
        if (s_logger.isTraceEnabled()) {
            s_logger.trace("ClusterId List having enough CPU and RAM capacity: " + clusterIdswithEnoughCapacity);
        }
        Pair<List<Long>, Map<Long, Double>> result = capacityDao.orderClustersByAggregateCapacity(id, capacityType, isZone);
        List<Long> clusterIdsOrderedByAggregateCapacity = result.first();
        //only keep the clusters that have enough capacity to host this VM
        if (s_logger.isTraceEnabled()) {
            s_logger.trace("ClusterId List in order of aggregate capacity: " + clusterIdsOrderedByAggregateCapacity);
        }
        clusterIdsOrderedByAggregateCapacity.retainAll(clusterIdswithEnoughCapacity);

        if (s_logger.isTraceEnabled()) {
            s_logger.trace("ClusterId List having enough CPU and RAM capacity & in order of aggregate capacity: " + clusterIdsOrderedByAggregateCapacity);
        }

        return result;

    }

};