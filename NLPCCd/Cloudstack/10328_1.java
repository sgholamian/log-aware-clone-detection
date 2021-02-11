//,temp,sample_4798.java,2,17,temp,sample_4787.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (loadBalancingRules == null || loadBalancingRules.isEmpty()) {
return null;
}
ExternalLoadBalancerDeviceVO lbDeviceVO = getExternalLoadBalancerForNetwork(network);
if (lbDeviceVO == null) {
return null;
}
HostVO externalLoadBalancer = _hostDao.findById(lbDeviceVO.getHostId());
boolean externalLoadBalancerIsInline = _networkMgr.isNetworkInlineMode(network);
if (network.getState() == Network.State.Allocated) {


log.info("external load balancer was asked to apply lb rules for network with id this network is not implemented skipping backend commands");
}
}

};