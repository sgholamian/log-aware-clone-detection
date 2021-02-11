//,temp,sample_5902.java,2,12,temp,sample_5910.java,2,12
//,3
public class xxx {
public boolean applyStaticNats(Network network, List<? extends StaticNat> rules) throws ResourceUnavailableException {
if (!_networkModel.isProviderSupportServiceInNetwork(network.getId(), Service.StaticNat, Provider.CiscoVnmc)) {
return false;
}
List<CiscoVnmcControllerVO> devices = _ciscoVnmcDao.listByPhysicalNetwork(network.getPhysicalNetworkId());
if (devices.isEmpty()) {


log.info("no cisco vnmc device on network");
}
}

};