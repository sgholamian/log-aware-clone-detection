//,temp,sample_8616.java,2,12,temp,sample_8614.java,2,12
//,2
public class xxx {
public boolean saveUserData(final Network network, final NicProfile nic, final VirtualMachineProfile vm) throws ResourceUnavailableException {
if (!canHandle(network, null)) {
return false;
}
final List<DomainRouterVO> routers = _routerDao.listByNetworkAndRole(network.getId(), Role.VIRTUAL_ROUTER);
if (routers == null || routers.isEmpty()) {


log.info("can t find virtual router element in network");
}
}

};