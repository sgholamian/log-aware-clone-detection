//,temp,VpcVirtualRouterElement.java,455,478,temp,VpcVirtualRouterElement.java,418,453
//,3
public class xxx {
    @Override
    public boolean deletePrivateGateway(final PrivateGateway gateway) throws ConcurrentOperationException, ResourceUnavailableException {
        if (gateway.getType() != VpcGateway.Type.Private) {
            s_logger.warn("Type of vpc gateway is not " + VpcGateway.Type.Private);
            return false;
        }

        final List<DomainRouterVO> routers = _vpcRouterMgr.getVpcRouters(gateway.getVpcId());
        if (routers == null || routers.isEmpty()) {
            s_logger.debug(getName() + " element doesn't need to delete Private gateway on the backend; VPC virtual " + "router doesn't exist in the vpc id=" + gateway.getVpcId());
            return true;
        }

        s_logger.info("Adding VPC routers to Guest Network: " + routers.size() + " to be added!");

        int result = 0;
        for (final DomainRouterVO domainRouterVO : routers) {
            if (_vpcRouterMgr.destroyPrivateGateway(gateway, domainRouterVO)) {
                result++;
            }
        }

        return result == routers.size() ? true : false;
    }

};