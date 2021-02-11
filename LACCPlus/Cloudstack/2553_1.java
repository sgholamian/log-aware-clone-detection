//,temp,FirstFitPlanner.java,493,525,temp,FirstFitPlanner.java,458,491
//,3
public class xxx {
    protected Pair<List<Long>, Map<Long, Double>> listPodsByCapacity(long zoneId, int requiredCpu, long requiredRam) {
        //look at the aggregate available cpu and ram per pod
        //although an aggregate value may be false indicator that a pod can host a vm, it will at the least eliminate those pods which definitely cannot

        //we need pods having enough cpu AND RAM to host this particular VM and order them by aggregate pod capacity
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Listing pods in order of aggregate capacity, that have (atleast one host with) enough CPU and RAM capacity under this Zone: " + zoneId);
        }
        String capacityTypeToOrder = configDao.getValue(Config.HostCapacityTypeToOrderClusters.key());
        short capacityType = Capacity.CAPACITY_TYPE_CPU;
        if ("RAM".equalsIgnoreCase(capacityTypeToOrder)) {
            capacityType = Capacity.CAPACITY_TYPE_MEMORY;
        }

        List<Long> podIdswithEnoughCapacity = capacityDao.listPodsByHostCapacities(zoneId, requiredCpu, requiredRam, capacityType);
        if (s_logger.isTraceEnabled()) {
            s_logger.trace("PodId List having enough CPU and RAM capacity: " + podIdswithEnoughCapacity);
        }
        Pair<List<Long>, Map<Long, Double>> result = capacityDao.orderPodsByAggregateCapacity(zoneId, capacityType);
        List<Long> podIdsOrderedByAggregateCapacity = result.first();
        //only keep the clusters that have enough capacity to host this VM
        if (s_logger.isTraceEnabled()) {
            s_logger.trace("PodId List in order of aggregate capacity: " + podIdsOrderedByAggregateCapacity);
        }
        podIdsOrderedByAggregateCapacity.retainAll(podIdswithEnoughCapacity);

        if (s_logger.isTraceEnabled()) {
            s_logger.trace("PodId List having enough CPU and RAM capacity & in order of aggregate capacity: " + podIdsOrderedByAggregateCapacity);
        }

        return result;

    }

};