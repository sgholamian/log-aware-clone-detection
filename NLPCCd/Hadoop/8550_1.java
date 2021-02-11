//,temp,sample_9347.java,2,14,temp,sample_2200.java,2,8
//,3
public class xxx {
protected void removeStoredToken(TimelineDelegationTokenIdentifier tokenId) throws IOException {
if (LOG.isDebugEnabled()) {
}
try {
if (stateStore != null) {
stateStore.removeToken(tokenId);
}
} catch (IOException e) {


log.info("unable to remove token");
}
}

};