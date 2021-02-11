//,temp,sample_7076.java,2,11,temp,sample_4385.java,2,11
//,2
public class xxx {
protected boolean canHandle(Network network, Service service) {
if (network.getBroadcastDomainType() != BroadcastDomainType.Lswitch) {
return false;
}
if (!networkModel.isProviderForNetwork(getProvider(), network.getId())) {


log.info("niciranvpelement is not a provider for network");
}
}

};