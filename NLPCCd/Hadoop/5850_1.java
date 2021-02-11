//,temp,sample_6113.java,2,10,temp,sample_6117.java,2,10
//,3
public class xxx {
protected void storeNewToken(RMDelegationTokenIdentifier identifier, long renewDate) {
try {
rm.getRMContext().getStateStore().storeRMDelegationToken(identifier, renewDate);
} catch (Exception e) {


log.info("error in storing rmdelegationtoken with sequence number");
}
}

};