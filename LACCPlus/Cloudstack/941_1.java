//,temp,JuniperSRXExternalFirewallElement.java,150,173,temp,PaloAltoExternalFirewallElement.java,179,198
//,3
public class xxx {
    @Override
    public boolean implement(Network network, NetworkOffering offering, DeployDestination dest, ReservationContext context) throws ResourceUnavailableException,
        ConcurrentOperationException, InsufficientNetworkCapacityException {
        DataCenter zone = _entityMgr.findById(DataCenter.class, network.getDataCenterId());

        // don't have to implement network is Basic zone
        if (zone.getNetworkType() == NetworkType.Basic) {
            s_logger.debug("Not handling network implement in zone of type " + NetworkType.Basic);
            return false;
        }

        if (!canHandle(network, null)) {
            return false;
        }

        try {
            return manageGuestNetworkWithExternalFirewall(true, network);
        } catch (InsufficientCapacityException capacityException) {
            // TODO: handle out of capacity exception in more gracefule manner when multiple providers are present for
            // the network
            s_logger.error("Fail to implement the JuniperSRX for network " + network, capacityException);
            return false;
        }
    }

};