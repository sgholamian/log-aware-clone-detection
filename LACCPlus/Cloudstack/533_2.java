//,temp,FirstFitPlanner.java,361,416,temp,FirstFitPlanner.java,221,280
//,3
public class xxx {
    private List<Long> scanPodsForDestination(VirtualMachineProfile vmProfile, DeploymentPlan plan, ExcludeList avoid) {

        ServiceOffering offering = vmProfile.getServiceOffering();
        int requiredCpu = offering.getCpu() * offering.getSpeed();
        long requiredRam = offering.getRamSize() * 1024L * 1024L;
        //list pods under this zone by cpu and ram capacity
        List<Long> prioritizedPodIds = new ArrayList<Long>();
        Pair<List<Long>, Map<Long, Double>> podCapacityInfo = listPodsByCapacity(plan.getDataCenterId(), requiredCpu, requiredRam);
        List<Long> podsWithCapacity = podCapacityInfo.first();

        if (!podsWithCapacity.isEmpty()) {
            if (avoid.getPodsToAvoid() != null) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Removing from the podId list these pods from avoid set: " + avoid.getPodsToAvoid());
                }
                podsWithCapacity.removeAll(avoid.getPodsToAvoid());
            }
            if (!isRootAdmin(vmProfile)) {
                List<Long> disabledPods = listDisabledPods(plan.getDataCenterId());
                if (!disabledPods.isEmpty()) {
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Removing from the podId list these pods that are disabled: " + disabledPods);
                    }
                    podsWithCapacity.removeAll(disabledPods);
                }
            }
        } else {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("No pods found having a host with enough capacity, returning.");
            }
            return null;
        }

        if (!podsWithCapacity.isEmpty()) {

            prioritizedPodIds = reorderPods(podCapacityInfo, vmProfile, plan);
            if (prioritizedPodIds == null || prioritizedPodIds.isEmpty()) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("No Pods found for destination, returning.");
                }
                return null;
            }

            List<Long> clusterList = new ArrayList<Long>();
            //loop over pods
            for (Long podId : prioritizedPodIds) {
                s_logger.debug("Checking resources under Pod: " + podId);
                List<Long> clustersUnderPod = scanClustersForDestinationInZoneOrPod(podId, false, vmProfile, plan, avoid);
                if (clustersUnderPod != null) {
                    clusterList.addAll(clustersUnderPod);
                }
            }
            return clusterList;
        } else {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("No Pods found after removing disabled pods and pods in avoid list, returning.");
            }
            return null;
        }
    }

};