//,temp,sample_7158.java,2,9,temp,sample_9349.java,2,14
//,3
public class xxx {
protected void updateStoredToken(TimelineDelegationTokenIdentifier tokenId, long renewDate) {
if (LOG.isDebugEnabled()) {
}
try {
if (stateStore != null) {
stateStore.updateToken(tokenId, renewDate);
}
} catch (IOException e) {


log.info("unable to update token");
}
}

};