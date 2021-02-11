//,temp,sample_4789.java,2,8,temp,sample_1390.java,2,8
//,2
public class xxx {
public boolean manageGuestNetworkWithExternalLoadBalancer(boolean add, Network guestConfig) throws ResourceUnavailableException, InsufficientCapacityException {
if (guestConfig.getTrafficType() != TrafficType.Guest) {


log.info("external load balancer can only be used for guest networks");
}
}

};