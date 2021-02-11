//,temp,NetscalerElement.java,214,233,temp,JuniperSRXExternalFirewallElement.java,127,148
//,3
public class xxx {
    private boolean canHandle(Network config, Service service) {
        DataCenter zone = _dcDao.findById(config.getDataCenterId());
        // Create a NCC Resource on Demand for the zone.

        boolean handleInAdvanceZone = (zone.getNetworkType() == NetworkType.Advanced
                && (config.getGuestType() == Network.GuestType.Isolated
                || config.getGuestType() == Network.GuestType.Shared)
                && config.getTrafficType() == TrafficType.Guest);
        boolean handleInBasicZone = (zone.getNetworkType() == NetworkType.Basic
                && config.getGuestType() == Network.GuestType.Shared && config.getTrafficType() == TrafficType.Guest);

        if (!(handleInAdvanceZone || handleInBasicZone)) {
            s_logger.trace("Not handling network with Type  " + config.getGuestType() + " and traffic type "
                    + config.getTrafficType() + " in zone of type " + zone.getNetworkType());
            return false;
        }

        return (_networkManager.isProviderForNetwork(getProvider(), config.getId()) && _ntwkSrvcDao
                .canProviderSupportServiceInNetwork(config.getId(), service, Network.Provider.Netscaler));
    }

};