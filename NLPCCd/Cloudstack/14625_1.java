//,temp,sample_9469.java,2,17,temp,sample_9476.java,2,17
//,2
public class xxx {
public void dummy_method(){
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


log.info("unable to obtain vm disk statistics");
}
}

};