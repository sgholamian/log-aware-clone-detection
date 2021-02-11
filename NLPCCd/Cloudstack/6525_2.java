//,temp,sample_4797.java,2,14,temp,sample_4786.java,2,14
//,3
public class xxx {
public boolean applyLoadBalancerRules(Network network, List<LoadBalancingRule> loadBalancingRules) throws ResourceUnavailableException {
long zoneId = network.getDataCenterId();
DataCenterVO zone = _dcDao.findById(zoneId);
if (loadBalancingRules == null || loadBalancingRules.isEmpty()) {
return true;
}
ExternalLoadBalancerDeviceVO lbDeviceVO = getExternalLoadBalancerForNetwork(network);
if (lbDeviceVO == null) {


log.info("there is no external load balancer device assigned to this network either network is not implement are already shutdown so just returning");
}
}

};