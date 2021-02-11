//,temp,BaremetalDhcpElement.java,97,105,temp,BaremetalPxeElement.java,105,117
//,3
public class xxx {
    @Override
    public boolean implement(Network network, NetworkOffering offering, DeployDestination dest, ReservationContext context) throws ConcurrentOperationException,
        ResourceUnavailableException, InsufficientCapacityException {
        if (offering.isSystemOnly() || !canHandle(dest, offering.getTrafficType(), network.getGuestType())) {
            s_logger.debug("BaremetalDhcpElement can not handle networkoffering: " + offering.getName());
            return false;
        }
        return true;
    }

};