//,temp,ClusterMO.java,620,638,temp,ClusterMO.java,541,556
//,3
public class xxx {
    @Override
    public ManagedObjectReference findMigrationTarget(VirtualMachineMO vmMo) throws Exception {
        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - findMigrationTarget(). target MOR: " + _mor.getValue() + ", vm: " + vmMo.getName());

        List<ClusterHostRecommendation> candidates = recommendHostsForVm(vmMo);
        if (candidates != null && candidates.size() > 0) {
            if (s_logger.isTraceEnabled())
                s_logger.trace("vCenter API trace - findMigrationTarget() done(successfully)");
            return candidates.get(0).getHost();
        }

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - findMigrationTarget() done(failed)");
        return null;
    }

};