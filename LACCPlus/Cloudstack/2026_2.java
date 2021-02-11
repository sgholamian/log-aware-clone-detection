//,temp,OvsElement.java,504,533,temp,VpcVirtualRouterElement.java,508,530
//,3
public class xxx {
    @Override
    public boolean applyNetworkACLs(final Network network, final List<? extends NetworkACLItem> rules) throws ResourceUnavailableException {
        boolean result = true;
        if (canHandle(network, Service.NetworkACL)) {
            final List<DomainRouterVO> routers = _routerDao.listByNetworkAndRole(network.getId(), Role.VIRTUAL_ROUTER);
            if (routers == null || routers.isEmpty()) {
                s_logger.debug("Virtual router elemnt doesn't need to apply firewall rules on the backend; virtual " + "router doesn't exist in the network " + network.getId());
                return true;
            }

            final DataCenterVO dcVO = _dcDao.findById(network.getDataCenterId());
            final NetworkTopology networkTopology = networkTopologyContext.retrieveNetworkTopology(dcVO);

            for (final DomainRouterVO domainRouterVO : routers) {
                try {
                    result = result && networkTopology.applyNetworkACLs(network, rules, domainRouterVO, false);
                } catch (final Exception ex) {
                    s_logger.debug("Failed to apply network acl in network " + network.getId());
                }
            }
        }
        return result;
    }

};