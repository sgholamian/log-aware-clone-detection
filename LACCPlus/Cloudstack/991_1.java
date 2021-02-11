//,temp,VirtualRouterElement.java,585,603,temp,VirtualRouterElement.java,416,441
//,3
public class xxx {
    @Override
    public boolean applyStaticNats(final Network network, final List<? extends StaticNat> rules) throws ResourceUnavailableException {
        boolean result = true;
        if (canHandle(network, Service.StaticNat)) {
            final List<DomainRouterVO> routers = getRouters(network);
            if (routers == null || routers.isEmpty()) {
                s_logger.debug("Virtual router elemnt doesn't need to apply static nat on the backend; virtual " + "router doesn't exist in the network " + network.getId());
                return true;
            }

            final DataCenterVO dcVO = _dcDao.findById(network.getDataCenterId());
            final NetworkTopology networkTopology = networkTopologyContext.retrieveNetworkTopology(dcVO);

            for (final DomainRouterVO domainRouterVO : routers) {
                result = result && networkTopology.applyStaticNats(network, rules, domainRouterVO);
            }
        }
        return result;
    }

};