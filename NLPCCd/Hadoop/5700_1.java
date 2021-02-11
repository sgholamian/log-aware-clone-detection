//,temp,sample_6798.java,2,14,temp,sample_5824.java,2,13
//,3
public class xxx {
public ResourceBlacklistRequest getBlacklistUpdates() {
ResourceBlacklistRequest ret;
List<String> blacklist = new ArrayList<>(blacklistNodes);
final int currentBlacklistSize = blacklist.size();
final double failureThreshold = this.blacklistDisableFailureThreshold * numberOfNodeManagerHosts;
if (currentBlacklistSize < failureThreshold) {
if (LOG.isDebugEnabled()) {


log.info("blacklist size is less than failure threshold ratio out of total usable nodes");
}
}
}

};