//,temp,sample_1102.java,2,16,temp,sample_1106.java,2,16
//,3
public class xxx {
public boolean shutdown(final Network network, final ReservationContext context, final boolean cleanup) throws ConcurrentOperationException, ResourceUnavailableException {
final Long vpcId = network.getVpcId();
if (vpcId == null) {
return true;
}
boolean success = true;
final List<? extends VirtualRouter> routers = _routerDao.listByVpcId(vpcId);
for (final VirtualRouter router : routers) {
if (!_networkMdl.isVmPartOfNetwork(router.getId(), network.getId())) {


log.info("router is not a part the network");
}
}
}

};