//,temp,sample_5804.java,2,14,temp,sample_7077.java,2,14
//,2
public class xxx {
protected boolean canHandle(final Network network, final Service service) {
if (network.getBroadcastDomainType() != BroadcastDomainType.Vswitch) {
return false;
}
if (!_networkModel.isProviderForNetwork(getProvider(), network.getId())) {
return false;
}
if (!_ntwkSrvcDao.canProviderSupportServiceInNetwork(network.getId(), service, Network.Provider.Ovs)) {


log.info("ovselement can t provide the service on network");
}
}

};