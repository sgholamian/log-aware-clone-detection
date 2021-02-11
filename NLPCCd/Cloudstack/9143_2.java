//,temp,sample_7224.java,2,8,temp,sample_7227.java,2,8
//,3
public class xxx {
public boolean prepare(Network network, NicProfile nicProfile, VirtualMachineProfile vm, DeployDestination dest, ReservationContext context) throws ConcurrentOperationException, ResourceUnavailableException, InsufficientCapacityException {
if (network.getTrafficType() == TrafficType.Guest) {


log.info("ignore network");
}
}

};