//,temp,sample_4398.java,2,9,temp,sample_1602.java,2,9
//,2
public class xxx {
public boolean implement(Network network, NetworkOffering offering, DeployDestination dest, ReservationContext context) throws ResourceUnavailableException, ConcurrentOperationException, InsufficientNetworkCapacityException {
DataCenter zone = _entityMgr.findById(DataCenter.class, network.getDataCenterId());
if (zone.getNetworkType() == NetworkType.Basic) {


log.info("not handling network implement in zone of type");
}
}

};