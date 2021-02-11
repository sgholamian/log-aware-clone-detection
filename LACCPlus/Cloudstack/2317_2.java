//,temp,BigSwitchBcfElement.java,196,213,temp,BrocadeVcsElement.java,140,157
//,3
public class xxx {
    protected boolean canHandle(Network network, Service service) {
        s_logger.debug("Checking if BrocadeVcsElement can handle service " + service.getName() + " on network " + network.getDisplayText());
        if (network.getBroadcastDomainType() != BroadcastDomainType.Vcs) {
            return false;
        }

        if (!_networkModel.isProviderForNetwork(getProvider(), network.getId())) {
            s_logger.debug("BrocadeVcsElement is not a provider for network " + network.getDisplayText());
            return false;
        }

        if (!_ntwkSrvcDao.canProviderSupportServiceInNetwork(network.getId(), service, Network.Provider.BrocadeVcs)) {
            s_logger.debug("BrocadeVcsElement can't provide the " + service.getName() + " service on network " + network.getDisplayText());
            return false;
        }

        return true;
    }

};