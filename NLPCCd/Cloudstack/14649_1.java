//,temp,sample_7085.java,2,11,temp,sample_4741.java,2,11
//,2
public class xxx {
public boolean prepare(Network network, NicProfile nic, VirtualMachineProfile vm, DeployDestination dest, ReservationContext context) throws ConcurrentOperationException, ResourceUnavailableException, InsufficientCapacityException {
if (!canHandle(network, Service.Connectivity)) {
return false;
}
if (network.getBroadcastUri() == null) {


log.info("nic has no broadcast uri with the lswitch uuid");
}
}

};