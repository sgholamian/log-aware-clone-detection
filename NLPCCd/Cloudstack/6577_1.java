//,temp,sample_9470.java,2,18,temp,sample_9477.java,2,18
//,3
public class xxx {
public void dummy_method(){
for (Long vmId : vmIds) {
UserVmVO vm = _vmDao.findById(vmId);
vmNames.add(vm.getInstanceName());
}
Answer answer = _agentMgr.easySend(hostId, new GetVmDiskStatsCommand(vmNames, _hostDao.findById(hostId).getGuid(), hostName));
if (answer == null || !answer.getResult()) {
return null;
} else {
HashMap<String, List<VmDiskStatsEntry>> vmDiskStatsByName = ((GetVmDiskStatsAnswer)answer).getVmDiskStatsMap();
if (vmDiskStatsByName == null) {


log.info("unable to obtain vm disk statistics");
}
}
}

};