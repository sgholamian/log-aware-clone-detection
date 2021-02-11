//,temp,sample_8836.java,2,15,temp,sample_8835.java,2,14
//,2
public class xxx {
protected boolean startVpc(final Vpc vpc, final DeployDestination dest, final ReservationContext context) throws ConcurrentOperationException, ResourceUnavailableException, InsufficientCapacityException {
boolean success = true;
final List<Provider> providersToImplement = getVpcProviders(vpc.getId());
for (final VpcProvider element : getVpcElements()) {
if (providersToImplement.contains(element.getProvider())) {
if (element.implementVpc(vpc, dest, context)) {
} else {


log.info("vpc failed to start");
}
}
}
}

};