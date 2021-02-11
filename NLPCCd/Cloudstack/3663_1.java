//,temp,sample_5905.java,2,8,temp,sample_5901.java,2,8
//,2
public class xxx {
public boolean applyPFRules(Network network, List<PortForwardingRule> rules) throws ResourceUnavailableException {
if (!_networkModel.isProviderSupportServiceInNetwork(network.getId(), Service.PortForwarding, Provider.CiscoVnmc)) {


log.info("port forwarding service is not provided by cisco vnmc device on network");
}
}

};