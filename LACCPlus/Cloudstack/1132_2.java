//,temp,UserVmManagerImpl.java,3923,3956,temp,UserVmManagerImpl.java,1893,1926
//,3
public class xxx {
    @Override
    public HashMap<Long, VmStatsEntry> getVirtualMachineStatistics(long hostId, String hostName, List<Long> vmIds) throws CloudRuntimeException {
        HashMap<Long, VmStatsEntry> vmStatsById = new HashMap<Long, VmStatsEntry>();

        if (vmIds.isEmpty()) {
            return vmStatsById;
        }

        List<String> vmNames = new ArrayList<String>();

        for (Long vmId : vmIds) {
            UserVmVO vm = _vmDao.findById(vmId);
            vmNames.add(vm.getInstanceName());
        }

        Answer answer = _agentMgr.easySend(hostId, new GetVmStatsCommand(vmNames, _hostDao.findById(hostId).getGuid(), hostName));
        if (answer == null || !answer.getResult()) {
            s_logger.warn("Unable to obtain VM statistics.");
            return null;
        } else {
            HashMap<String, VmStatsEntry> vmStatsByName = ((GetVmStatsAnswer)answer).getVmStatsMap();

            if (vmStatsByName == null) {
                s_logger.warn("Unable to obtain VM statistics.");
                return null;
            }

            for (Map.Entry<String, VmStatsEntry> entry : vmStatsByName.entrySet()) {
                vmStatsById.put(vmIds.get(vmNames.indexOf(entry.getKey())), entry.getValue());
            }
        }

        return vmStatsById;
    }

};