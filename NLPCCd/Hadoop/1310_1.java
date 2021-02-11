//,temp,sample_5158.java,2,10,temp,sample_5157.java,2,7
//,3
public class xxx {
private void storeOrUpdateRMDelegationTokenState( RMDelegationTokenIdentifier identifier, Long renewDate, boolean isUpdate) throws Exception {
Path nodeCreatePath = getNodePath(rmDTSecretManagerRoot, DELEGATION_TOKEN_PREFIX + identifier.getSequenceNumber());
RMDelegationTokenIdentifierData identifierData = new RMDelegationTokenIdentifierData(identifier, renewDate);
if (isUpdate) {


log.info("updating rmdelegationtoken");
}
}

};