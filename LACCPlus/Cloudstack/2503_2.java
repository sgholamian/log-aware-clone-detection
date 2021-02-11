//,temp,VpcVirtualRouterElement.java,455,478,temp,VpcVirtualRouterElement.java,418,453
//,3
public class xxx {
    @Override
    public boolean createPrivateGateway(final PrivateGateway gateway) throws ConcurrentOperationException, ResourceUnavailableException {
        if (gateway.getType() != VpcGateway.Type.Private) {
            s_logger.warn("Type of vpc gateway is not " + VpcGateway.Type.Private);
            return true;
        }

        final List<DomainRouterVO> routers = _vpcRouterMgr.getVpcRouters(gateway.getVpcId());
        if (routers == null || routers.isEmpty()) {
            s_logger.debug(getName() + " element doesn't need to create Private gateway on the backend; VPC virtual " + "router doesn't exist in the vpc id=" + gateway.getVpcId());
            return true;
        }

        s_logger.info("Adding VPC routers to Guest Network: " + routers.size() + " to be added!");

        final DataCenterVO dcVO = _dcDao.findById(gateway.getZoneId());
        final NetworkTopology networkTopology = networkTopologyContext.retrieveNetworkTopology(dcVO);

        boolean result = true;
        final Network network = _networkDao.findById(gateway.getNetworkId());
        final boolean isPrivateGateway = true;

        for (final DomainRouterVO domainRouterVO : routers) {
            if (networkTopology.setupPrivateGateway(gateway, domainRouterVO)) {
                try {
                    final List<NetworkACLItemVO> rules = _networkACLItemDao.listByACL(gateway.getNetworkACLId());
                    result = result && networkTopology.applyNetworkACLs(network, rules, domainRouterVO, isPrivateGateway);
                } catch (final Exception ex) {
                    s_logger.debug("Failed to apply network acl id  " + gateway.getNetworkACLId() + "  on gateway ");
                    return false;
                }
            }
        }

        return result;
    }

};