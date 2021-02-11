//,temp,UserDispersingPlanner.java,115,125,temp,UserDispersingPlanner.java,99,113
//,3
public class xxx {
    protected Pair<List<Long>, Map<Long, Double>> listClustersByUserDispersion(long id, boolean isZone, long accountId) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Applying Userdispersion heuristic to clusters for account: " + accountId);
        }
        Pair<List<Long>, Map<Long, Double>> clusterIdsVmCountInfo;
        if (isZone) {
            clusterIdsVmCountInfo = vmInstanceDao.listClusterIdsInZoneByVmCount(id, accountId);
        } else {
            clusterIdsVmCountInfo = vmInstanceDao.listClusterIdsInPodByVmCount(id, accountId);
        }
        if (s_logger.isTraceEnabled()) {
            s_logger.trace("List of clusters in ascending order of number of VMs: " + clusterIdsVmCountInfo.first());
        }
        return clusterIdsVmCountInfo;
    }

};