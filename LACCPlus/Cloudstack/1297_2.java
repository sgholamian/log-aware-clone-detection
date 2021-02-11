//,temp,OvsElement.java,458,479,temp,VirtualRouterElement.java,328,350
//,3
public class xxx {
    @Override
    public boolean applyLBRules(final Network network, final List<LoadBalancingRule> rules) throws ResourceUnavailableException {
        boolean result = true;
        if (canHandle(network, Service.Lb)) {
            if (!canHandleLbRules(rules)) {
                return false;
            }

            final List<DomainRouterVO> routers = getRouters(network);
            if (routers == null || routers.isEmpty()) {
                s_logger.debug("Virtual router elemnt doesn't need to apply lb rules on the backend; virtual " + "router doesn't exist in the network " + network.getId());
                return true;
            }

            final DataCenterVO dcVO = _dcDao.findById(network.getDataCenterId());
            final NetworkTopology networkTopology = networkTopologyContext.retrieveNetworkTopology(dcVO);

            for (final DomainRouterVO domainRouterVO : routers) {
                result = result && networkTopology.applyLoadBalancingRules(network, rules, domainRouterVO);
            }
        }
        return result;
    }

};