//,temp,sample_1097.java,2,16,temp,sample_1098.java,2,17
//,2
public class xxx {
protected void configureGuestNetwork(final Network network, final List<DomainRouterVO> routers ) throws ConcurrentOperationException, InsufficientCapacityException, ResourceUnavailableException {
for (final DomainRouterVO router : routers) {
if (!_networkMdl.isVmPartOfNetwork(router.getId(), network.getId())) {
final Map<VirtualMachineProfile.Param, Object> paramsForRouter = new HashMap<VirtualMachineProfile.Param, Object>(1);
if (network.getState() == State.Setup) {
paramsForRouter.put(VirtualMachineProfile.Param.ReProgramGuestNetworks, true);
}
if (!_vpcRouterMgr.addVpcRouterToGuestNetwork(router, network, paramsForRouter)) {
} else {


log.info("successfully added vpc router to guest network");
}
}
}
}

};