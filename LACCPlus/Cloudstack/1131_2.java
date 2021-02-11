//,temp,UserVmManagerImpl.java,1893,1926,temp,UserVmManagerImpl.java,1655,1688
//,3
public class xxx {
    @Override
    public HashMap<Long, List<VmDiskStatsEntry>> getVmDiskStatistics(long hostId, String hostName, List<Long> vmIds) throws CloudRuntimeException {
        HashMap<Long, List<VmDiskStatsEntry>> vmDiskStatsById = new HashMap<Long, List<VmDiskStatsEntry>>();

        if (vmIds.isEmpty()) {
            return vmDiskStatsById;
        }

        List<String> vmNames = new ArrayList<String>();

        for (Long vmId : vmIds) {
            UserVmVO vm = _vmDao.findById(vmId);
            vmNames.add(vm.getInstanceName());
        }

        Answer answer = _agentMgr.easySend(hostId, new GetVmDiskStatsCommand(vmNames, _hostDao.findById(hostId).getGuid(), hostName));
        if (answer == null || !answer.getResult()) {
            s_logger.warn("Unable to obtain VM disk statistics.");
            return null;
        } else {
            HashMap<String, List<VmDiskStatsEntry>> vmDiskStatsByName = ((GetVmDiskStatsAnswer)answer).getVmDiskStatsMap();

            if (vmDiskStatsByName == null) {
                s_logger.warn("Unable to obtain VM disk statistics.");
                return null;
            }

            for (Map.Entry<String, List<VmDiskStatsEntry>> entry: vmDiskStatsByName.entrySet()) {
                vmDiskStatsById.put(vmIds.get(vmNames.indexOf(entry.getKey())), entry.getValue());
            }
        }

        return vmDiskStatsById;
    }

};