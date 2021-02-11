//,temp,VirtualRouterElement.java,352,374,temp,VpcVirtualRouterElement.java,556,582
//,3
public class xxx {
    @Override
    public boolean applyACLItemsToPrivateGw(final PrivateGateway gateway, final List<? extends NetworkACLItem> rules) throws ResourceUnavailableException {
        final Network network = _networkDao.findById(gateway.getNetworkId());
        final boolean isPrivateGateway = true;

        final List<DomainRouterVO> routers = _vpcRouterMgr.getVpcRouters(gateway.getVpcId());
        if (routers == null || routers.isEmpty()) {
            s_logger.debug("Virtual router element doesn't need to apply network acl rules on the backend; virtual " + "router doesn't exist in the network " + network.getId());
            return true;
        }

        final DataCenterVO dcVO = _dcDao.findById(network.getDataCenterId());
        final NetworkTopology networkTopology = networkTopologyContext.retrieveNetworkTopology(dcVO);

        final Network privateNetwork = _networkModel.getNetwork(gateway.getNetworkId());

        boolean result = true;
        for (final DomainRouterVO domainRouterVO : routers) {
            final NicProfile nicProfile = _networkModel.getNicProfile(domainRouterVO, privateNetwork.getId(), null);
            if (nicProfile != null) {
                result = result && networkTopology.applyNetworkACLs(network, rules, domainRouterVO, isPrivateGateway);
            } else {
                s_logger.warn("Nic Profile for router '" + domainRouterVO + "' has already been removed. Router is redundant = " + domainRouterVO.getIsRedundantRouter());
            }
        }
        return result;
    }

};