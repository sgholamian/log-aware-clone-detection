//,temp,sample_3340.java,2,9,temp,sample_1988.java,2,9
//,2
public class xxx {
public void shutdown(NetworkProfile profile, NetworkOffering offering) {
NetworkVO networkObject = _networkDao.findById(profile.getId());
if (networkObject.getBroadcastDomainType() != BroadcastDomainType.Vlan || networkObject.getBroadcastUri() == null) {


log.info("broadcasturi is empty or incorrect for guestnetwork");
}
}

};