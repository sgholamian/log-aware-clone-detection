//,temp,sample_2000.java,2,10,temp,sample_3335.java,2,10
//,2
public class xxx {
protected boolean canHandle(NetworkOffering offering, NetworkType networkType, PhysicalNetwork physicalNetwork) {
if (networkType == NetworkType.Advanced && isMyTrafficType(offering.getTrafficType()) && offering.getGuestType() == Network.GuestType.Isolated && isMyIsolationMethod(physicalNetwork)) {
return true;
} else {


log.info("we only take care of guest networks of type in zone of type using isolation method mido");
}
}

};