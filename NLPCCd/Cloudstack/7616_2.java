//,temp,sample_8608.java,2,15,temp,sample_8606.java,2,15
//,2
public class xxx {
public boolean startVpn(final RemoteAccessVpn vpn) throws ResourceUnavailableException {
if (vpn.getNetworkId() == null) {
return false;
}
final Network network = _networksDao.findById(vpn.getNetworkId());
if (canHandle(network, Service.Vpn)) {
final List<DomainRouterVO> routers = _routerDao.listByNetworkAndRole(network.getId(), Role.VIRTUAL_ROUTER);
if (routers == null || routers.isEmpty()) {


log.info("virtual router elemnt doesn t need stop vpn on the backend virtual router doesn t exist in the network");
}
}
}

};