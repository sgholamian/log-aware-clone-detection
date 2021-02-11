//,temp,JuniperSRXExternalFirewallElement.java,127,148,temp,PaloAltoExternalFirewallElement.java,121,141
//,3
public class xxx {
    private boolean canHandle(Network network, Service service) {
        DataCenter zone = _entityMgr.findById(DataCenter.class, network.getDataCenterId());
        if ((zone.getNetworkType() == NetworkType.Advanced && !(network.getGuestType() == Network.GuestType.Isolated || network.getGuestType() == Network.GuestType.Shared)) ||
            (zone.getNetworkType() == NetworkType.Basic && network.getGuestType() != Network.GuestType.Shared)) {
            s_logger.trace("Element " + getProvider().getName() + "is not handling network type = " + network.getGuestType());
            return false;
        }

        if (service == null) {
            if (!_networkManager.isProviderForNetwork(getProvider(), network.getId())) {
                s_logger.trace("Element " + getProvider().getName() + " is not a provider for the network " + network);
                return false;
            }
        } else {
            if (!_networkManager.isProviderSupportServiceInNetwork(network.getId(), service, getProvider())) {
                s_logger.trace("Element " + getProvider().getName() + " doesn't support service " + service.getName() + " in the network " + network);
                return false;
            }
        }

        return true;
    }

};