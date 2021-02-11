//,temp,sample_4386.java,2,14,temp,sample_7077.java,2,14
//,3
public class xxx {
private boolean canHandle(Network network, Service service) {
if (network.getBroadcastDomainType() != BroadcastDomainType.Vlan) {
return false;
}
if (!_networkModel.isProviderForNetwork(getProvider(), network.getId())) {
return false;
}
if (!_ntwkSrvcDao.canProviderSupportServiceInNetwork(network.getId(), service, BcfConstants.BIG_SWITCH_BCF)) {


log.info("bigswitchbcfelement can t provide the service on network");
}
}

};