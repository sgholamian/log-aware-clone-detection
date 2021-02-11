//,temp,sample_9341.java,2,14,temp,sample_7520.java,2,11
//,3
public class xxx {
protected void storeNewMasterKey(DelegationKey key) throws IOException {
if (LOG.isDebugEnabled()) {
}
try {
if (stateStore != null) {
stateStore.storeTokenMasterKey(key);
}
} catch (IOException e) {


log.info("unable to store master key");
}
}

};