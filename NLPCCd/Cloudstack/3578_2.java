//,temp,sample_7076.java,2,11,temp,sample_4385.java,2,11
//,2
public class xxx {
private boolean canHandle(Network network, Service service) {
if (network.getBroadcastDomainType() != BroadcastDomainType.Vlan) {
return false;
}
if (!_networkModel.isProviderForNetwork(getProvider(), network.getId())) {


log.info("bigswitchbcfelement is not a provider for network");
}
}

};