//,temp,OvsElement.java,504,533,temp,VpcVirtualRouterElement.java,480,506
//,3
public class xxx {
    @Override
    public boolean applyLBRules(final Network network, final List<LoadBalancingRule> rules)
            throws ResourceUnavailableException {
        boolean result = true;
        if (canHandle(network, Service.Lb)) {
            if (!canHandleLbRules(rules)) {
                return false;
            }

            final List<DomainRouterVO> routers = _routerDao.listByNetworkAndRole(
                    network.getId(), Role.VIRTUAL_ROUTER);
            if (routers == null || routers.isEmpty()) {
                s_logger.debug("Virtual router elemnt doesn't need to apply load balancing rules on the backend; virtual "
                        + "router doesn't exist in the network "
                        + network.getId());
                return true;
            }

            final DataCenterVO dcVO = _dcDao.findById(network.getDataCenterId());
            final NetworkTopology networkTopology = _networkTopologyContext.retrieveNetworkTopology(dcVO);

            for (final DomainRouterVO domainRouterVO : routers) {
                result = result && networkTopology.applyLoadBalancingRules(network, rules, domainRouterVO);
                if (!result) {
                    s_logger.debug("Failed to apply load balancing rules in network " + network.getId());
                }
            }
        }
        return result;
    }

};