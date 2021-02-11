//,temp,sample_3260.java,2,10,temp,sample_7537.java,2,10
//,2
public class xxx {
protected boolean canHandle(NetworkOffering offering, final NetworkType networkType, final PhysicalNetwork physicalNetwork) {
if (networkType == NetworkType.Advanced && isMyTrafficType(offering.getTrafficType()) && offering.getGuestType() == Network.GuestType.Isolated && isMyIsolationMethod(physicalNetwork) && _ntwkOfferingSrvcDao.areServicesSupportedByNetworkOffering( offering.getId(), Service.Connectivity)) {
return true;
} else {


log.info("we only take care of guest networks of type in zone of type");
}
}

};