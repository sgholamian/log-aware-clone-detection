//,temp,UserDispersingPlanner.java,115,125,temp,UserConcentratedPodPlanner.java,97,110
//,3
public class xxx {
    protected Pair<List<Long>, Map<Long, Double>> listPodsByUserDispersion(long dataCenterId, long accountId) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Applying Userdispersion heuristic to pods for account: " + accountId);
        }
        Pair<List<Long>, Map<Long, Double>> podIdsVmCountInfo = vmInstanceDao.listPodIdsInZoneByVmCount(dataCenterId, accountId);
        if (s_logger.isTraceEnabled()) {
            s_logger.trace("List of pods in ascending order of number of VMs: " + podIdsVmCountInfo.first());
        }

        return podIdsVmCountInfo;
    }

};