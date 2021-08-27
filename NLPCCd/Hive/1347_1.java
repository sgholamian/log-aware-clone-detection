//,temp,sample_1763.java,2,10,temp,sample_3809.java,2,9
//,3
public class xxx {
public void onRemove(TezAmInstance serviceInstance, int ephSeqVersion) {
String sessionId = serviceInstance.getSessionId();
SessionType session = bySessionId.get(sessionId);
if (session != null) {


log.info("am for v has unregistered updating");
}
}

};