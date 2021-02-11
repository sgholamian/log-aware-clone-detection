//,temp,OvsElement.java,118,139,temp,NiciraNvpElement.java,191,208
//,2
public class xxx {
    protected boolean canHandle(Network network, Service service) {
        s_logger.debug("Checking if NiciraNvpElement can handle service " + service.getName() + " on network " + network.getDisplayText());
        if (network.getBroadcastDomainType() != BroadcastDomainType.Lswitch) {
            return false;
        }

        if (!networkModel.isProviderForNetwork(getProvider(), network.getId())) {
            s_logger.debug("NiciraNvpElement is not a provider for network " + network.getDisplayText());
            return false;
        }

        if (!ntwkSrvcDao.canProviderSupportServiceInNetwork(network.getId(), service, Network.Provider.NiciraNvp)) {
            s_logger.debug("NiciraNvpElement can't provide the " + service.getName() + " service on network " + network.getDisplayText());
            return false;
        }

        return true;
    }

};