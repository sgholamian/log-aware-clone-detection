//,temp,sample_7771.java,2,10,temp,sample_510.java,2,10
//,3
public class xxx {
protected boolean canHandle(NetworkOffering offering, final NetworkType networkType, final PhysicalNetwork physicalNetwork) {
if (networkType == NetworkType.Advanced && isMyTrafficType(offering.getTrafficType()) && offering.getGuestType() == Network.GuestType.Isolated && isMyIsolationMethod(physicalNetwork)) {
return true;
} else {


log.info("we only take care of guest networks of type in zone of type");
}
}

};