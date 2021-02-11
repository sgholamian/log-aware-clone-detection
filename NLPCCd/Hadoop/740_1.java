//,temp,sample_7272.java,2,13,temp,sample_2647.java,2,10
//,3
public class xxx {
protected void containerFailedOnHost(String hostName) {
if (!nodeBlacklistingEnabled) {
return;
}
if (blacklistedNodes.contains(hostName)) {
if (LOG.isDebugEnabled()) {


log.info("host is already blacklisted");
}
}
}

};