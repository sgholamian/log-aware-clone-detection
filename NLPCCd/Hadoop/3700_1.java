//,temp,sample_8260.java,2,12,temp,sample_2647.java,2,10
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