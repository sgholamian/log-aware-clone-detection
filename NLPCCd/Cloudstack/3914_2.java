//,temp,sample_5902.java,2,12,temp,sample_5906.java,2,12
//,2
public class xxx {
public boolean applyPFRules(Network network, List<PortForwardingRule> rules) throws ResourceUnavailableException {
if (!_networkModel.isProviderSupportServiceInNetwork(network.getId(), Service.PortForwarding, Provider.CiscoVnmc)) {
return false;
}
List<CiscoVnmcControllerVO> devices = _ciscoVnmcDao.listByPhysicalNetwork(network.getPhysicalNetworkId());
if (devices.isEmpty()) {


log.info("no cisco vnmc device on network");
}
}

};