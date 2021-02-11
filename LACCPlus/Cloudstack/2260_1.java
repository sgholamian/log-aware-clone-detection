//,temp,InternalLoadBalancerElement.java,179,192,temp,InternalLoadBalancerElement.java,167,177
//,3
public class xxx {
    @Override
    public boolean prepare(Network network, NicProfile nic, VirtualMachineProfile vm, DeployDestination dest, ReservationContext context)
        throws ConcurrentOperationException, ResourceUnavailableException, InsufficientCapacityException {

        if (!canHandle(network, null)) {
            s_logger.trace("No need to prepare " + getName());
            return true;
        }

        if (vm.getType() == VirtualMachine.Type.User) {
            return implementInternalLbVms(network, dest);
        }
        return true;
    }

};