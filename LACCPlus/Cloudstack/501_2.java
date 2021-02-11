//,temp,OvsElement.java,458,479,temp,VirtualRouterElement.java,376,394
//,3
public class xxx {
    @Override
    public boolean startVpn(final RemoteAccessVpn vpn) throws ResourceUnavailableException {
        if (vpn.getNetworkId() == null) {
            return false;
        }

        final Network network = _networksDao.findById(vpn.getNetworkId());
        if (canHandle(network, Service.Vpn)) {
            final List<DomainRouterVO> routers = _routerDao.listByNetworkAndRole(network.getId(), Role.VIRTUAL_ROUTER);
            if (routers == null || routers.isEmpty()) {
                s_logger.debug("Virtual router elemnt doesn't need stop vpn on the backend; virtual router doesn't" + " exist in the network " + network.getId());
                return true;
            }
            return _routerMgr.startRemoteAccessVpn(network, vpn, routers);
        } else {
            s_logger.debug("Element " + getName() + " doesn't handle createVpn command");
            return false;
        }
    }

};