//,temp,sample_8260.java,2,12,temp,sample_7741.java,2,11
//,3
public class xxx {
protected void removeStoredMasterKey(DelegationKey key) {
if (LOG.isDebugEnabled()) {
}
try {
store.removeTokenMasterKey(key);
} catch (IOException e) {


log.info("unable to remove master key");
}
}

};