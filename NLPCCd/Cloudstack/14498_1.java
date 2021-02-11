//,temp,sample_1988.java,2,9,temp,sample_3263.java,2,9
//,2
public class xxx {
public void shutdown(final NetworkProfile profile, final NetworkOffering offering) {
final NetworkVO networkObject = networkDao.findById(profile.getId());
if (networkObject.getBroadcastDomainType() != BroadcastDomainType.Lswitch || networkObject.getBroadcastUri() == null) {


log.info("broadcasturi is empty or incorrect for guestnetwork");
}
}

};