//,temp,VirtualRouterElement.java,396,414,temp,VirtualRouterElement.java,352,374
//,3
public class xxx {
    @Override
    public String[] applyVpnUsers(final RemoteAccessVpn vpn, final List<? extends VpnUser> users) throws ResourceUnavailableException {
        if (vpn.getNetworkId() == null) {
            return null;
        }

        final Network network = _networksDao.findById(vpn.getNetworkId());
        if (canHandle(network, Service.Vpn)) {
            final List<DomainRouterVO> routers = _routerDao.listByNetworkAndRole(network.getId(), Role.VIRTUAL_ROUTER);
            if (routers == null || routers.isEmpty()) {
                s_logger.debug("Virtual router elemnt doesn't need to apply vpn users on the backend; virtual router" + " doesn't exist in the network " + network.getId());
                return null;
            }

            final DataCenterVO dcVO = _dcDao.findById(network.getDataCenterId());
            final NetworkTopology networkTopology = networkTopologyContext.retrieveNetworkTopology(dcVO);

            return networkTopology.applyVpnUsers(network, users, routers);
        } else {
            s_logger.debug("Element " + getName() + " doesn't handle applyVpnUsers command");
            return null;
        }
    }

};