//,temp,sample_3580.java,2,10,temp,sample_3581.java,2,11
//,3
public class xxx {
public VmwareHypervisorHostNetworkSummary getHyperHostNetworkSummary(String esxServiceConsolePort) throws Exception {
List<ManagedObjectReference> hosts = _context.getVimClient().getDynamicProperty(_mor, "host");
if (hosts != null && hosts.size() > 0) {
VmwareHypervisorHostNetworkSummary summary = new HostMO(_context, hosts.get(0)).getHyperHostNetworkSummary(esxServiceConsolePort);
return summary;
}


log.info("vcenter api trace gethyperhostresourcesummary done failed");
}

};