//,temp,OvsElement.java,149,162,temp,BrocadeVcsElement.java,166,176
//,2
public class xxx {
    @Override
    public boolean implement(final Network network, final NetworkOffering offering,
            final DeployDestination dest, final ReservationContext context)
                    throws ConcurrentOperationException, ResourceUnavailableException,
                    InsufficientCapacityException {
        s_logger.debug("entering OvsElement implement function for network "
                + network.getDisplayText() + " (state " + network.getState()
                + ")");

        if (!canHandle(network, Service.Connectivity)) {
            return false;
        }
        return true;
    }

};