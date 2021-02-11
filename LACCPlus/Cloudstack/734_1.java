//,temp,VpcVirtualRouterElement.java,696,713,temp,VpcVirtualRouterElement.java,677,694
//,2
public class xxx {
    @Override
    public boolean stopVpn(final RemoteAccessVpn vpn) throws ResourceUnavailableException {
        if (vpn.getVpcId() == null) {
            return false;
        }

        final List<DomainRouterVO> routers = _vpcRouterMgr.getVpcRouters(vpn.getVpcId());
        if (routers == null) {
            s_logger.debug("Cannot apply vpn users on the backend; virtual router doesn't exist in the network " + vpn.getVpcId());
            return false;
        }

        boolean result = true;
        for (final DomainRouterVO domainRouterVO : routers) {
            result = result && _vpcRouterMgr.stopRemoteAccessVpn(vpn, domainRouterVO);
        }
        return result;
    }

};