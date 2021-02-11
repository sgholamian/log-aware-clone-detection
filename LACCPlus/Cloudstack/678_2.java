//,temp,JuniperSRXExternalFirewallElement.java,186,205,temp,PaloAltoExternalFirewallElement.java,179,198
//,2
public class xxx {
    @Override
    public boolean shutdown(Network network, ReservationContext context, boolean cleanup) throws ResourceUnavailableException, ConcurrentOperationException {
        DataCenter zone = _entityMgr.findById(DataCenter.class, network.getDataCenterId());

        // don't have to implement network is Basic zone
        if (zone.getNetworkType() == NetworkType.Basic) {
            s_logger.debug("Not handling network shutdown in zone of type " + NetworkType.Basic);
            return false;
        }

        if (!canHandle(network, null)) {
            return false;
        }
        try {
            return manageGuestNetworkWithExternalFirewall(false, network);
        } catch (InsufficientCapacityException capacityException) {
            // TODO: handle out of capacity exception
            return false;
        }
    }

};