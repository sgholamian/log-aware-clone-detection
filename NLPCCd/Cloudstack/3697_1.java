//,temp,sample_1120.java,2,12,temp,sample_8601.java,2,12
//,2
public class xxx {
public boolean applyNetworkACLs(final Network network, final List<? extends NetworkACLItem> rules) throws ResourceUnavailableException {
boolean result = true;
if (canHandle(network, Service.NetworkACL)) {
final List<DomainRouterVO> routers = _routerDao.listByNetworkAndRole(network.getId(), Role.VIRTUAL_ROUTER);
if (routers == null || routers.isEmpty()) {


log.info("virtual router elemnt doesn t need to apply firewall rules on the backend virtual router doesn t exist in the network");
}
}
}

};