//,temp,sample_1101.java,2,9,temp,sample_1105.java,2,9
//,3
public class xxx {
public boolean shutdown(final Network network, final ReservationContext context, final boolean cleanup) throws ConcurrentOperationException, ResourceUnavailableException {
final Long vpcId = network.getVpcId();
if (vpcId == null) {


log.info("network doesn t belong to any vpc so skipping unplug nic part");
}
}

};