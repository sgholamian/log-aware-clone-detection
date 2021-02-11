//,temp,sample_3913.java,2,12,temp,sample_3912.java,2,11
//,3
public class xxx {
public void scheduleRestartForVmsOnHost(final HostVO host, boolean investigate) {
if (host.getType() != Host.Type.Routing) {
return;
}
if (host.getHypervisorType() == HypervisorType.VMware || host.getHypervisorType() == HypervisorType.Hyperv) {


log.info("don t restart vms on host as it is a host");
}
}

};