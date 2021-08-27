//,temp,sample_1762.java,2,12,temp,sample_1764.java,2,12
//,3
public class xxx {
public void onRemove(TezAmInstance serviceInstance, int ephSeqVersion) {
String sessionId = serviceInstance.getSessionId();
SessionType session = bySessionId.get(sessionId);
if (session != null) {
session.updateFromRegistry(null, ephSeqVersion);
} else {


log.info("am for an unknown has unregistered ignoring");
}
}

};