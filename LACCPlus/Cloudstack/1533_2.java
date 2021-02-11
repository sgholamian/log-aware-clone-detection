//,temp,XcpServerDiscoverer.java,653,670,temp,HypervServerDiscoverer.java,383,404
//,2
public class xxx {
    @Override
    public final HostVO createHostVOForDirectConnectAgent(final HostVO host, final StartupCommand[] startup, final ServerResource resource,
        final Map<String, String> details, final List<String> hostTags) {
        StartupCommand firstCmd = startup[0];
        if (!(firstCmd instanceof StartupRoutingCommand)) {
            return null;
        }

        StartupRoutingCommand ssCmd = ((StartupRoutingCommand)firstCmd);
        if (ssCmd.getHypervisorType() != HypervisorType.Hyperv) {
            return null;
        }

        s_logger.info("Host: " + host.getName() + " connected with hypervisor type: " + HypervisorType.Hyperv + ". Checking CIDR...");

        HostPodVO pod = _podDao.findById(host.getPodId());
        DataCenterVO dc = _dcDao.findById(host.getDataCenterId());

        _resourceMgr.checkCIDR(pod, dc, ssCmd.getPrivateIpAddress(), ssCmd.getPrivateNetmask());

        return _resourceMgr.fillRoutingHostVO(host, ssCmd, HypervisorType.Hyperv, details, hostTags);
    }

};