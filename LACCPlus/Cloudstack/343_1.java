//,temp,UserVmManagerImpl.java,3923,3956,temp,UserVmManagerImpl.java,1655,1688
//,3
public class xxx {
    @Override
    public HashMap<Long, List<VmNetworkStatsEntry>> getVmNetworkStatistics(long hostId, String hostName, List<Long> vmIds) {
        HashMap<Long, List<VmNetworkStatsEntry>> vmNetworkStatsById = new HashMap<Long, List<VmNetworkStatsEntry>>();

        if (vmIds.isEmpty()) {
            return vmNetworkStatsById;
        }

        List<String> vmNames = new ArrayList<String>();

        for (Long vmId : vmIds) {
            UserVmVO vm = _vmDao.findById(vmId);
            vmNames.add(vm.getInstanceName());
        }

        Answer answer = _agentMgr.easySend(hostId, new GetVmNetworkStatsCommand(vmNames, _hostDao.findById(hostId).getGuid(), hostName));
        if (answer == null || !answer.getResult()) {
            s_logger.warn("Unable to obtain VM network statistics.");
            return null;
        } else {
            HashMap<String, List<VmNetworkStatsEntry>> vmNetworkStatsByName = ((GetVmNetworkStatsAnswer)answer).getVmNetworkStatsMap();

            if (vmNetworkStatsByName == null) {
                s_logger.warn("Unable to obtain VM network statistics.");
                return null;
            }

            for (String vmName : vmNetworkStatsByName.keySet()) {
                vmNetworkStatsById.put(vmIds.get(vmNames.indexOf(vmName)), vmNetworkStatsByName.get(vmName));
            }
        }

        return vmNetworkStatsById;
    }

};