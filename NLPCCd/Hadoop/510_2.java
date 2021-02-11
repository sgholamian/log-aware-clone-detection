//,temp,sample_4884.java,2,9,temp,sample_8266.java,2,12
//,3
public class xxx {
protected void updateStoredToken(MRDelegationTokenIdentifier tokenId, long renewDate) {
if (LOG.isDebugEnabled()) {
}
try {
store.updateToken(tokenId, renewDate);
} catch (IOException e) {


log.info("unable to update token");
}
}

};