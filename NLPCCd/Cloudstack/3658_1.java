//,temp,sample_6715.java,2,11,temp,sample_4385.java,2,11
//,2
public class xxx {
protected boolean canHandle(Network network, Service service) {
if (network.getBroadcastDomainType() != BroadcastDomainType.Vcs) {
return false;
}
if (!_networkModel.isProviderForNetwork(getProvider(), network.getId())) {


log.info("brocadevcselement is not a provider for network");
}
}

};