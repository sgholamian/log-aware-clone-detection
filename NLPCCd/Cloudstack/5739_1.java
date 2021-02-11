//,temp,sample_5909.java,2,8,temp,sample_5901.java,2,8
//,3
public class xxx {
public boolean applyStaticNats(Network network, List<? extends StaticNat> rules) throws ResourceUnavailableException {
if (!_networkModel.isProviderSupportServiceInNetwork(network.getId(), Service.StaticNat, Provider.CiscoVnmc)) {


log.info("static nat service is not provided by cisco vnmc device on network");
}
}

};