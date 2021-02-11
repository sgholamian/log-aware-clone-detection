//,temp,sample_5810.java,2,15,temp,sample_8603.java,2,15
//,2
public class xxx {
public boolean applyLBRules(final Network network, final List<LoadBalancingRule> rules) throws ResourceUnavailableException {
boolean result = true;
if (canHandle(network, Service.Lb)) {
if (!canHandleLbRules(rules)) {
return false;
}
final List<DomainRouterVO> routers = _routerDao.listByNetworkAndRole(network.getId(), Role.VIRTUAL_ROUTER);
if (routers == null || routers.isEmpty()) {


log.info("virtual router elemnt doesn t need to apply lb rules on the backend virtual router doesn t exist in the network");
}
}
}

};