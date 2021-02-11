//,temp,sample_6113.java,2,10,temp,sample_6117.java,2,10
//,3
public class xxx {
protected void removeStoredToken(RMDelegationTokenIdentifier ident) throws IOException {
try {
rm.getRMContext().getStateStore().removeRMDelegationToken(ident);
} catch (Exception e) {


log.info("error in removing rmdelegationtoken with sequence number");
}
}

};