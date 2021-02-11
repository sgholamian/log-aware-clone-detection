//,temp,sample_4789.java,2,8,temp,sample_1390.java,2,8
//,2
public class xxx {
public boolean manageGuestNetworkWithExternalFirewall(boolean add, Network network) throws ResourceUnavailableException, InsufficientCapacityException {
if (network.getTrafficType() != TrafficType.Guest) {


log.info("external firewall can only be used for add remove guest networks");
}
}

};