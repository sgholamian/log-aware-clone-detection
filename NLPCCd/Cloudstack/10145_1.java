//,temp,sample_5911.java,2,16,temp,sample_5903.java,2,16
//,3
public class xxx {
public boolean applyStaticNats(Network network, List<? extends StaticNat> rules) throws ResourceUnavailableException {
if (!_networkModel.isProviderSupportServiceInNetwork(network.getId(), Service.StaticNat, Provider.CiscoVnmc)) {
return false;
}
List<CiscoVnmcControllerVO> devices = _ciscoVnmcDao.listByPhysicalNetwork(network.getPhysicalNetworkId());
if (devices.isEmpty()) {
return true;
}
NetworkAsa1000vMapVO asaForNetwork = _networkAsa1000vMapDao.findByNetworkId(network.getId());
if (asaForNetwork == null) {


log.info("cisco asa device is not associated with network");
}
}

};