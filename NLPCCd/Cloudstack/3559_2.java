//,temp,sample_3263.java,2,9,temp,sample_7641.java,2,9
//,2
public class xxx {
public void shutdown(NetworkProfile profile, NetworkOffering offering) {
NetworkVO networkObject = networkDao.findById(profile.getId());
if (networkObject.getBroadcastDomainType() != BroadcastDomainType.OpenDaylight || networkObject.getBroadcastUri() == null) {


log.info("broadcasturi is empty or incorrect for guestnetwork");
}
}

};