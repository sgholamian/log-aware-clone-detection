//,temp,sample_8264.java,2,12,temp,sample_9364.java,2,13
//,3
public class xxx {
protected void removeStoredToken(MRDelegationTokenIdentifier tokenId) throws IOException {
if (LOG.isDebugEnabled()) {
}
try {
store.removeToken(tokenId);
} catch (IOException e) {


log.info("unable to remove token");
}
}

};