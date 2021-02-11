//,temp,sample_1.java,2,12,temp,sample_0.java,2,12
//,2
public class xxx {
public void create() throws ResourceAllocationException {
try {
StaticRoute result = _vpcService.createStaticRoute(getGatewayId(), getCidr());
setEntityId(result.getId());
setEntityUuid(result.getUuid());
} catch (NetworkRuleConflictException ex) {


log.info("network rule conflict");
}
}

};