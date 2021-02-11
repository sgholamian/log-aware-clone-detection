//,temp,sample_1115.java,2,14,temp,sample_1118.java,2,14
//,3
public class xxx {
public boolean createPrivateGateway(final PrivateGateway gateway) throws ConcurrentOperationException, ResourceUnavailableException {
if (gateway.getType() != VpcGateway.Type.Private) {
return true;
}
final List<DomainRouterVO> routers = _vpcRouterMgr.getVpcRouters(gateway.getVpcId());
if (routers == null || routers.isEmpty()) {
s_logger.debug(getName() + " element doesn't need to create Private gateway on the backend; VPC virtual " + "router doesn't exist in the vpc id=" + gateway.getVpcId());
return true;
}


log.info("adding vpc routers to guest network to be added");
}

};