//,temp,ZKRMStateStore.java,774,784,temp,ZKRMStateStore.java,686,697
//,2
public class xxx {
  @Override
  protected synchronized void removeRMDelegationTokenState(
      RMDelegationTokenIdentifier rmDTIdentifier) throws Exception {
    String nodeRemovePath =
        getNodePath(delegationTokensRootPath, DELEGATION_TOKEN_PREFIX
            + rmDTIdentifier.getSequenceNumber());
    if (LOG.isDebugEnabled()) {
      LOG.debug("Removing RMDelegationToken_"
          + rmDTIdentifier.getSequenceNumber());
    }
    safeDelete(nodeRemovePath);
  }

};