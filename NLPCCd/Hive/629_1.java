//,temp,sample_1762.java,2,12,temp,sample_1764.java,2,12
//,3
public class xxx {
public void onCreate(TezAmInstance si, int ephSeqVersion) {
String sessionId = si.getSessionId();
SessionType session = bySessionId.get(sessionId);
if (session != null) {
session.updateFromRegistry(si, ephSeqVersion);
} else {


log.info("am for an unknown has registered ignoring");
}
}

};