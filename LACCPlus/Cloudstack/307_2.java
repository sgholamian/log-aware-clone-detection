//,temp,RouterControlHelper.java,46,65,temp,InternalLoadBalancerVMManagerImpl.java,498,515
//,3
public class xxx {
    protected String getInternalLbControlIp(final long internalLbVmId) {
        String controlIpAddress = null;
        final List<NicVO> nics = _nicDao.listByVmId(internalLbVmId);
        for (final NicVO nic : nics) {
            final Network ntwk = _ntwkModel.getNetwork(nic.getNetworkId());
            if (ntwk.getTrafficType() == TrafficType.Control) {
                controlIpAddress = nic.getIPv4Address();
            }
        }

        if (controlIpAddress == null) {
            s_logger.warn("Unable to find Internal LB control ip in its attached NICs!. Internal LB vm: " + internalLbVmId);
            final DomainRouterVO internalLbVm = _internalLbVmDao.findById(internalLbVmId);
            return internalLbVm.getPrivateIpAddress();
        }

        return controlIpAddress;
    }

};