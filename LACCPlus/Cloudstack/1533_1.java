//,temp,XcpServerDiscoverer.java,653,670,temp,HypervServerDiscoverer.java,383,404
//,2
public class xxx {
    @Override
    public HostVO createHostVOForDirectConnectAgent(HostVO host, StartupCommand[] startup, ServerResource resource, Map<String, String> details, List<String> hostTags) {
        StartupCommand firstCmd = startup[0];
        if (!(firstCmd instanceof StartupRoutingCommand)) {
            return null;
        }

        StartupRoutingCommand ssCmd = ((StartupRoutingCommand)firstCmd);
        if (ssCmd.getHypervisorType() != HypervisorType.XenServer) {
            return null;
        }

        HostPodVO pod = _podDao.findById(host.getPodId());
        DataCenterVO dc = _dcDao.findById(host.getDataCenterId());
        s_logger.info("Host: " + host.getName() + " connected with hypervisor type: " + HypervisorType.XenServer + ". Checking CIDR...");
        _resourceMgr.checkCIDR(pod, dc, ssCmd.getPrivateIpAddress(), ssCmd.getPrivateNetmask());
        return _resourceMgr.fillRoutingHostVO(host, ssCmd, HypervisorType.XenServer, details, hostTags);
    }

};