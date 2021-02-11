//,temp,sample_8264.java,2,12,temp,sample_8620.java,2,9
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