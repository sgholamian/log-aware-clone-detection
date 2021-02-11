//,temp,sample_9605.java,2,13,temp,sample_9345.java,2,14
//,3
public class xxx {
protected void storeNewToken(TimelineDelegationTokenIdentifier tokenId, long renewDate) {
if (LOG.isDebugEnabled()) {
}
try {
if (stateStore != null) {
stateStore.storeToken(tokenId, renewDate);
}
} catch (IOException e) {


log.info("unable to store token");
}
}

};