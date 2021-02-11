//,temp,OvsElement.java,481,502,temp,VirtualRouterElement.java,828,846
//,3
public class xxx {
    @Override
    public boolean applyPFRules(final Network network, final List<PortForwardingRule> rules)
            throws ResourceUnavailableException {
        if (!canHandle(network, Service.PortForwarding)) {
            return false;
        }
        final List<DomainRouterVO> routers = _routerDao.listByNetworkAndRole(
                network.getId(), Role.VIRTUAL_ROUTER);
        if (routers == null || routers.isEmpty()) {
            s_logger.debug("Ovs element doesn't need to apply firewall rules on the backend; virtual "
                    + "router doesn't exist in the network " + network.getId());
            return true;
        }

        boolean result = true;
        final DataCenterVO dcVO = _dcDao.findById(network.getDataCenterId());
        final NetworkTopology networkTopology = _networkTopologyContext.retrieveNetworkTopology(dcVO);
        for (final DomainRouterVO domainRouterVO : routers) {
            result = result && networkTopology.applyFirewallRules(network, rules, domainRouterVO);
        }
        return result;
    }

};