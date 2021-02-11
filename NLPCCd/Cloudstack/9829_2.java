//,temp,sample_5336.java,2,18,temp,sample_4379.java,2,18
//,3
public class xxx {
public void dummy_method(){
List<HostVO> vmWareServerHosts = _resourceMgr.listAllUpAndEnabledHostsInOneZoneByHypervisor(HypervisorType.VMware, scope.getScopeId());
List<HostVO> kvmHosts = _resourceMgr.listAllUpAndEnabledHostsInOneZoneByHypervisor(HypervisorType.KVM, scope.getScopeId());
List<HostVO> hosts = new ArrayList<HostVO>();
hosts.addAll(xenServerHosts);
hosts.addAll(vmWareServerHosts);
hosts.addAll(kvmHosts);
for (HostVO host : hosts) {
try {
_storageMgr.connectHostToSharedPool(host.getId(), dataStore.getId());
} catch (Exception e) {


log.info("unable to establish a connection between and");
}
}
}

};