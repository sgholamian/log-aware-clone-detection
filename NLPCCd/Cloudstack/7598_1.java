//,temp,sample_949.java,2,17,temp,sample_1020.java,2,17
//,2
public class xxx {
public void dummy_method(){
try {
if (!checkVmOnHost(vm, destHostId)) {
try {
_agentMgr.send(srcHostId, new Commands(cleanup(vm.getInstanceName())), null);
} catch (final AgentUnavailableException e) {
}
cleanup(vmGuru, new VirtualMachineProfileImpl(vm), work, Event.AgentReportStopped, true);
throw new CloudRuntimeException("VM not found on desintation host. Unable to complete migration for " + vm);
}
} catch (final OperationTimedoutException e) {


log.info("error while checking the vm is on host");
}
}

};