//,temp,sample_5803.java,2,11,temp,sample_4385.java,2,11
//,2
public class xxx {
protected boolean canHandle(final Network network, final Service service) {
if (network.getBroadcastDomainType() != BroadcastDomainType.Vswitch) {
return false;
}
if (!_networkModel.isProviderForNetwork(getProvider(), network.getId())) {


log.info("ovselement is not a provider for network");
}
}

};