//,temp,sample_1020.java,2,17,temp,sample_938.java,2,17
//,3
public class xxx {
public void dummy_method(){
try {
if (!checkVmOnHost(vm, dstHostId)) {
try {
_agentMgr.send(srcHostId, new Commands(cleanup(vm.getInstanceName())), null);
} catch (final AgentUnavailableException e) {
}
cleanup(vmGuru, new VirtualMachineProfileImpl(vm), work, Event.AgentReportStopped, true);
throw new CloudRuntimeException("Unable to complete migration for " + vm);
}
} catch (final OperationTimedoutException e) {


log.info("error while checking the vm on host");
}
}

};