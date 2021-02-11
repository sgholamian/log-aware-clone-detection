//,temp,sample_5905.java,2,8,temp,sample_5901.java,2,8
//,2
public class xxx {
public boolean applyFWRules(Network network, List<? extends FirewallRule> rules) throws ResourceUnavailableException {
if (!_networkModel.isProviderSupportServiceInNetwork(network.getId(), Service.Firewall, Provider.CiscoVnmc)) {


log.info("firewall service is not provided by cisco vnmc device on network");
}
}

};