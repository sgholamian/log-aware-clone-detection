//,temp,sample_4883.java,2,8,temp,sample_7163.java,2,10
//,3
public class xxx {
private void storeOrUpdateRMDT(RMDelegationTokenIdentifier tokenId, Long renewDate, boolean isUpdate) throws IOException {
String tokenKey = getRMDTTokenNodeKey(tokenId);
RMDelegationTokenIdentifierData tokenData = new RMDelegationTokenIdentifierData(tokenId, renewDate);
if (LOG.isDebugEnabled()) {


log.info("storing token to");
}
}

};