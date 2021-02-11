//,temp,sample_4386.java,2,14,temp,sample_7077.java,2,14
//,3
public class xxx {
protected boolean canHandle(Network network, Service service) {
if (network.getBroadcastDomainType() != BroadcastDomainType.Lswitch) {
return false;
}
if (!networkModel.isProviderForNetwork(getProvider(), network.getId())) {
return false;
}
if (!ntwkSrvcDao.canProviderSupportServiceInNetwork(network.getId(), service, Network.Provider.NiciraNvp)) {


log.info("niciranvpelement can t provide the service on network");
}
}

};