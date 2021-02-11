//,temp,sample_1604.java,2,9,temp,sample_4400.java,2,9
//,2
public class xxx {
public boolean shutdown(Network network, ReservationContext context, boolean cleanup) throws ResourceUnavailableException, ConcurrentOperationException {
DataCenter zone = _entityMgr.findById(DataCenter.class, network.getDataCenterId());
if (zone.getNetworkType() == NetworkType.Basic) {


log.info("not handling network shutdown in zone of type");
}
}

};