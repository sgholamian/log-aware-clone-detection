//,temp,sample_7103.java,2,12,temp,sample_7105.java,2,12
//,3
public class xxx {
public boolean applyPFRules(Network network, List<PortForwardingRule> rules) throws ResourceUnavailableException {
if (!canHandle(network, Service.PortForwarding)) {
return false;
}
List<NiciraNvpDeviceVO> devices = niciraNvpDao.listByPhysicalNetwork(network.getPhysicalNetworkId());
if (devices.isEmpty()) {


log.info("no niciranvp controller on physical network");
}
}

};