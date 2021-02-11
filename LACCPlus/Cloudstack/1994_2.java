//,temp,RandomStoragePoolAllocator.java,37,79,temp,RandomAllocator.java,109,162
//,3
public class xxx {
    @Override
    public List<Host> allocateTo(VirtualMachineProfile vmProfile, DeploymentPlan plan, Type type, ExcludeList avoid, int returnUpTo, boolean considerReservedCapacity) {

        long dcId = plan.getDataCenterId();
        Long podId = plan.getPodId();
        Long clusterId = plan.getClusterId();
        ServiceOffering offering = vmProfile.getServiceOffering();

        List<Host> suitableHosts = new ArrayList<Host>();

        if (type == Host.Type.Storage) {
            return suitableHosts;
        }

        String hostTag = offering.getHostTag();
        if (hostTag != null) {
            s_logger.debug("Looking for hosts in dc: " + dcId + "  pod:" + podId + "  cluster:" + clusterId + " having host tag:" + hostTag);
        } else {
            s_logger.debug("Looking for hosts in dc: " + dcId + "  pod:" + podId + "  cluster:" + clusterId);
        }

        // list all computing hosts, regardless of whether they support routing...it's random after all
        List<? extends Host> hosts = new ArrayList<HostVO>();
        if (hostTag != null) {
            hosts = _hostDao.listByHostTag(type, clusterId, podId, dcId, hostTag);
        } else {
            hosts = _resourceMgr.listAllUpAndEnabledHosts(type, clusterId, podId, dcId);
        }

        s_logger.debug("Random Allocator found " + hosts.size() + "  hosts");

        if (hosts.size() == 0) {
            return suitableHosts;
        }

        Collections.shuffle(hosts);
        for (Host host : hosts) {
            if (suitableHosts.size() == returnUpTo) {
                break;
            }

            if (!avoid.shouldAvoid(host)) {
                suitableHosts.add(host);
            } else {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Host name: " + host.getName() + ", hostId: " + host.getId() + " is in avoid set, skipping this and trying other available hosts");
                }
            }
        }
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Random Host Allocator returning " + suitableHosts.size() + " suitable hosts");
        }
        return suitableHosts;
    }

};