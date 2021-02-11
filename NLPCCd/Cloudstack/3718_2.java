//,temp,sample_8610.java,2,18,temp,sample_1119.java,2,18
//,2
public class xxx {
public void dummy_method(){
for (final Service service : services) {
if (!canHandle(network, service)) {
canHandle = false;
break;
}
}
boolean result = true;
if (canHandle) {
final List<DomainRouterVO> routers = _routerDao.listByNetworkAndRole(network.getId(), Role.VIRTUAL_ROUTER);
if (routers == null || routers.isEmpty()) {


log.info("element doesn t need to associate ip addresses on the backend vpc virtual router doesn t exist in the network");
}
}
}

};