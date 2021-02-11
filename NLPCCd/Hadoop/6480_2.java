//,temp,sample_1983.java,2,9,temp,sample_8258.java,2,12
//,3
public class xxx {
protected void storeNewMasterKey(DelegationKey key) throws IOException {
if (LOG.isDebugEnabled()) {
}
try {
store.storeTokenMasterKey(key);
} catch (IOException e) {


log.info("unable to store master key");
}
}

};