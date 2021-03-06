//,temp,OvsElement.java,425,456,temp,VirtualRouterElement.java,416,441
//,3
public class xxx {
    @Override
    public boolean applyIps(final Network network,
            final List<? extends PublicIpAddress> ipAddress, final Set<Service> services)
                    throws ResourceUnavailableException {
        boolean canHandle = true;
        for (final Service service : services) {
            // check if Ovs can handle services except SourceNat & Firewall
            if (!canHandle(network, service) && service != Service.SourceNat && service != Service.Firewall) {
                canHandle = false;
                break;
            }
        }
        boolean result = true;
        if (canHandle) {
            final List<DomainRouterVO> routers = _routerDao.listByNetworkAndRole(
                    network.getId(), Role.VIRTUAL_ROUTER);
            if (routers == null || routers.isEmpty()) {
                s_logger.debug("Virtual router element doesn't need to associate ip addresses on the backend; virtual "
                        + "router doesn't exist in the network "
                        + network.getId());
                return true;
            }

            final DataCenterVO dcVO = _dcDao.findById(network.getDataCenterId());
            final NetworkTopology networkTopology = _networkTopologyContext.retrieveNetworkTopology(dcVO);

            for (final DomainRouterVO domainRouterVO : routers) {
                result = result && networkTopology.associatePublicIP(network, ipAddress, domainRouterVO);
            }
        }
        return result;
    }

};