//,temp,sample_8262.java,2,12,temp,sample_7499.java,2,11
//,3
public class xxx {
protected void storeNewToken(MRDelegationTokenIdentifier tokenId, long renewDate) {
if (LOG.isDebugEnabled()) {
}
try {
store.storeToken(tokenId, renewDate);
} catch (IOException e) {


log.info("unable to store token");
}
}

};