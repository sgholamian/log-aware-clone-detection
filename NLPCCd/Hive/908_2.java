//,temp,sample_3287.java,2,16,temp,sample_3288.java,2,17
//,3
public class xxx {
private synchronized void performBackgroundWork() {
Map<String, Long> terminatedHosts = Maps.newHashMap();
synchronized (mTerminatedHosts) {
terminatedHosts.putAll(mTerminatedHosts);
}
for (NodeMetadata node : getRunningNodes()) {
String ip = publicIpOrHostname(node);
if (terminatedHosts.containsKey(ip)) {
terminateInternal(node);
} else if(!mLiveHosts.containsKey(ip)) {


log.info("found zombie node previously unknown to ptest");
}
}
}

};