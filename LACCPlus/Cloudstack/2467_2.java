//,temp,UserDispersingPlanner.java,115,125,temp,UserConcentratedPodPlanner.java,97,110
//,3
public class xxx {
    protected List<Long> listPodsByUserConcentration(long zoneId, long accountId) {

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Applying UserConcentratedPod heuristic for account: " + accountId);
        }

        List<Long> prioritizedPods = vmDao.listPodIdsHavingVmsforAccount(zoneId, accountId);

        if (s_logger.isTraceEnabled()) {
            s_logger.trace("List of pods to be considered, after applying UserConcentratedPod heuristic: " + prioritizedPods);
        }

        return prioritizedPods;
    }

};