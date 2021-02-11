//,temp,BigSwitchBcfElement.java,196,213,temp,BrocadeVcsElement.java,140,157
//,3
public class xxx {
    private boolean canHandle(Network network, Service service) {
        s_logger.debug("Checking if BigSwitchBcfElement can handle service " + service.getName() + " on network " + network.getDisplayText());
        if (network.getBroadcastDomainType() != BroadcastDomainType.Vlan) {
            return false;
        }

        if (!_networkModel.isProviderForNetwork(getProvider(), network.getId())) {
            s_logger.debug("BigSwitchBcfElement is not a provider for network " + network.getDisplayText());
            return false;
        }

        if (!_ntwkSrvcDao.canProviderSupportServiceInNetwork(network.getId(), service, BcfConstants.BIG_SWITCH_BCF)) {
            s_logger.debug("BigSwitchBcfElement can't provide the " + service.getName() + " service on network " + network.getDisplayText());
            return false;
        }

        return true;
    }

};