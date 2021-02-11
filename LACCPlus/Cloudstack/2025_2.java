//,temp,OvsElement.java,504,533,temp,OvsElement.java,458,479
//,3
public class xxx {
    @Override
    public boolean applyStaticNats(final Network network, final List<? extends StaticNat> rules)
            throws ResourceUnavailableException {
        if (!canHandle(network, Service.StaticNat)) {
            return false;
        }
        final List<DomainRouterVO> routers = _routerDao.listByNetworkAndRole(
                network.getId(), Role.VIRTUAL_ROUTER);
        if (routers == null || routers.isEmpty()) {
            s_logger.debug("Ovs element doesn't need to apply static nat on the backend; virtual "
                    + "router doesn't exist in the network " + network.getId());
            return true;
        }

        final DataCenterVO dcVO = _dcDao.findById(network.getDataCenterId());
        final NetworkTopology networkTopology = _networkTopologyContext.retrieveNetworkTopology(dcVO);
        boolean result = true;
        for (final DomainRouterVO domainRouterVO : routers) {
            result = result && networkTopology.applyStaticNats(network, rules, domainRouterVO);
        }
        return result;
    }

};