//,temp,ContrailElementImpl.java,120,143,temp,ContrailGuru.java,141,181
//,3
public class xxx {
    @Override
    public boolean implement(Network network, NetworkOffering offering, DeployDestination dest, ReservationContext context) throws ConcurrentOperationException,
        ResourceUnavailableException, InsufficientCapacityException {
        s_logger.debug("NetworkElement implement: " + network.getName() + ", traffic type: " + network.getTrafficType());
        if (network.getTrafficType() == TrafficType.Guest) {
            s_logger.debug("ignore network " + network.getName());
            return true;
        }
        VirtualNetworkModel vnModel = _manager.getDatabase().lookupVirtualNetwork(network.getUuid(), _manager.getCanonicalName(network), network.getTrafficType());

        if (vnModel == null) {
            vnModel = new VirtualNetworkModel(network, network.getUuid(), _manager.getCanonicalName(network), network.getTrafficType());
            vnModel.setProperties(_manager.getModelController(), network);
        }
        try {
            if (!vnModel.verify(_manager.getModelController())) {
                vnModel.update(_manager.getModelController());
            }
            _manager.getDatabase().getVirtualNetworks().add(vnModel);
        } catch (Exception ex) {
            s_logger.warn("virtual-network update: ", ex);
        }
        return true;
    }

};