//,temp,FirstFitPlanner.java,361,416,temp,FirstFitPlanner.java,221,280
//,3
public class xxx {
    private List<Long> scanClustersForDestinationInZoneOrPod(long id, boolean isZone, VirtualMachineProfile vmProfile, DeploymentPlan plan, ExcludeList avoid) {

        VirtualMachine vm = vmProfile.getVirtualMachine();
        ServiceOffering offering = vmProfile.getServiceOffering();
        DataCenter dc = dcDao.findById(vm.getDataCenterId());
        int requiredCpu = offering.getCpu() * offering.getSpeed();
        long requiredRam = offering.getRamSize() * 1024L * 1024L;

        //list clusters under this zone by cpu and ram capacity
        Pair<List<Long>, Map<Long, Double>> clusterCapacityInfo = listClustersByCapacity(id, requiredCpu, requiredRam, avoid, isZone);
        List<Long> prioritizedClusterIds = clusterCapacityInfo.first();
        if (!prioritizedClusterIds.isEmpty()) {
            if (avoid.getClustersToAvoid() != null) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Removing from the clusterId list these clusters from avoid set: " + avoid.getClustersToAvoid());
                }
                prioritizedClusterIds.removeAll(avoid.getClustersToAvoid());
            }

            if (!isRootAdmin(vmProfile)) {
                List<Long> disabledClusters = new ArrayList<Long>();
                if (isZone) {
                    disabledClusters = listDisabledClusters(plan.getDataCenterId(), null);
                } else {
                    disabledClusters = listDisabledClusters(plan.getDataCenterId(), id);
                }
                if (!disabledClusters.isEmpty()) {
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Removing from the clusterId list these clusters that are disabled/clusters under disabled pods: " + disabledClusters);
                    }
                    prioritizedClusterIds.removeAll(disabledClusters);
                }
            }

            removeClustersCrossingThreshold(prioritizedClusterIds, avoid, vmProfile, plan);
            String hostTagOnOffering = offering.getHostTag();
            if (hostTagOnOffering != null) {
                removeClustersWithoutMatchingTag(prioritizedClusterIds, hostTagOnOffering);
            }

        } else {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("No clusters found having a host with enough capacity, returning.");
            }
            return null;
        }
        if (!prioritizedClusterIds.isEmpty()) {
            List<Long> clusterList = reorderClusters(id, isZone, clusterCapacityInfo, vmProfile, plan);
            return clusterList; //return checkClustersforDestination(clusterList, vmProfile, plan, avoid, dc);
        } else {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("No clusters found after removing disabled clusters and clusters in avoid list, returning.");
            }
            return null;
        }
    }

};