//,temp,ClusterMO.java,620,638,temp,ClusterMO.java,541,556
//,3
public class xxx {
    @Override
    public VmwareHypervisorHostNetworkSummary getHyperHostNetworkSummary(String esxServiceConsolePort) throws Exception {

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - getHyperHostNetworkSummary(). target MOR: " + _mor.getValue() + ", mgmtPortgroup: " + esxServiceConsolePort);

        List<ManagedObjectReference> hosts = _context.getVimClient().getDynamicProperty(_mor, "host");
        if (hosts != null && hosts.size() > 0) {
            VmwareHypervisorHostNetworkSummary summary = new HostMO(_context, hosts.get(0)).getHyperHostNetworkSummary(esxServiceConsolePort);

            if (s_logger.isTraceEnabled())
                s_logger.trace("vCenter API trace - getHyperHostResourceSummary() done(successfully)");
            return summary;
        }

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - getHyperHostResourceSummary() done(failed)");
        return null;
    }

};