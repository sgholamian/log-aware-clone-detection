//,temp,OvsElement.java,118,139,temp,NiciraNvpElement.java,191,208
//,2
public class xxx {
    protected boolean canHandle(final Network network, final Service service) {
        s_logger.debug("Checking if OvsElement can handle service "
                + service.getName() + " on network " + network.getDisplayText());
        if (network.getBroadcastDomainType() != BroadcastDomainType.Vswitch) {
            return false;
        }

        if (!_networkModel.isProviderForNetwork(getProvider(), network.getId())) {
            s_logger.debug("OvsElement is not a provider for network "
                    + network.getDisplayText());
            return false;
        }

        if (!_ntwkSrvcDao.canProviderSupportServiceInNetwork(network.getId(),
                service, Network.Provider.Ovs)) {
            s_logger.debug("OvsElement can't provide the " + service.getName()
                    + " service on network " + network.getDisplayText());
            return false;
        }

        return true;
    }

};