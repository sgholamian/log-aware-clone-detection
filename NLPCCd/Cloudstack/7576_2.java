//,temp,sample_5804.java,2,14,temp,sample_6716.java,2,14
//,2
public class xxx {
protected boolean canHandle(Network network, Service service) {
if (network.getBroadcastDomainType() != BroadcastDomainType.Vcs) {
return false;
}
if (!_networkModel.isProviderForNetwork(getProvider(), network.getId())) {
return false;
}
if (!_ntwkSrvcDao.canProviderSupportServiceInNetwork(network.getId(), service, Network.Provider.BrocadeVcs)) {


log.info("brocadevcselement can t provide the service on network");
}
}

};