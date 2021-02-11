//,temp,sample_9343.java,2,14,temp,sample_4877.java,2,8
//,3
public class xxx {
protected void removeStoredMasterKey(DelegationKey key) {
if (LOG.isDebugEnabled()) {
}
try {
if (stateStore != null) {
stateStore.removeTokenMasterKey(key);
}
} catch (IOException e) {


log.info("unable to remove master key");
}
}

};