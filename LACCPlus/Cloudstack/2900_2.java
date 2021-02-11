//,temp,OvsElement.java,149,162,temp,BrocadeVcsElement.java,166,176
//,2
public class xxx {
    @Override
    public boolean implement(Network network, NetworkOffering offering, DeployDestination dest, ReservationContext context) throws ConcurrentOperationException,
            ResourceUnavailableException, InsufficientCapacityException {
        s_logger.debug("entering BrocadeVcsElement implement function for network " + network.getDisplayText() + " (state " + network.getState() + ")");

        if (!canHandle(network, Service.Connectivity)) {
            return false;
        }

        return true;
    }

};