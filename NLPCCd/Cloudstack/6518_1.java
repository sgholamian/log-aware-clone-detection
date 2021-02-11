//,temp,sample_8611.java,2,12,temp,sample_1120.java,2,12
//,3
public class xxx {
public boolean applyStaticNats(final Network network, final List<? extends StaticNat> rules) throws ResourceUnavailableException {
boolean result = true;
if (canHandle(network, Service.StaticNat)) {
final List<DomainRouterVO> routers = _routerDao.listByNetworkAndRole(network.getId(), Role.VIRTUAL_ROUTER);
if (routers == null || routers.isEmpty()) {


log.info("virtual router elemnt doesn t need to apply static nat on the backend virtual router doesn t exist in the network");
}
}
}

};