//,temp,AbstractStoragePoolAllocator.java,127,155,temp,FirstFitAllocator.java,377,405
//,3
public class xxx {
    private List<? extends Host> reorderHostsByNumberOfVms(DeploymentPlan plan, List<? extends Host> hosts, Account account) {
        if (account == null) {
            return hosts;
        }
        long dcId = plan.getDataCenterId();
        Long podId = plan.getPodId();
        Long clusterId = plan.getClusterId();

        List<Long> hostIdsByVmCount = _vmInstanceDao.listHostIdsByVmCount(dcId, podId, clusterId, account.getAccountId());
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("List of hosts in ascending order of number of VMs: " + hostIdsByVmCount);
        }

        //now filter the given list of Hosts by this ordered list
        Map<Long, Host> hostMap = new HashMap<Long, Host>();
        for (Host host : hosts) {
            hostMap.put(host.getId(), host);
        }
        List<Long> matchingHostIds = new ArrayList<Long>(hostMap.keySet());

        hostIdsByVmCount.retainAll(matchingHostIds);

        List<Host> reorderedHosts = new ArrayList<Host>();
        for (Long id : hostIdsByVmCount) {
            reorderedHosts.add(hostMap.get(id));
        }

        return reorderedHosts;
    }

};