//,temp,InternalLoadBalancerElement.java,179,192,temp,InternalLoadBalancerElement.java,167,177
//,3
public class xxx {
    @Override
    public boolean implement(Network network, NetworkOffering offering, DeployDestination dest, ReservationContext context) throws ConcurrentOperationException,
        ResourceUnavailableException, InsufficientCapacityException {

        if (!canHandle(network, null)) {
            s_logger.trace("No need to implement " + getName());
            return true;
        }

        return implementInternalLbVms(network, dest);
    }

};