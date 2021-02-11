//,temp,ZKRMStateStore.java,774,784,temp,ZKRMStateStore.java,686,697
//,2
public class xxx {
  @Override
  protected synchronized void removeRMDTMasterKeyState(
      DelegationKey delegationKey) throws Exception {
    String nodeRemovePath =
        getNodePath(dtMasterKeysRootPath, DELEGATION_KEY_PREFIX
            + delegationKey.getKeyId());
    if (LOG.isDebugEnabled()) {
      LOG.debug("Removing RMDelegationKey_" + delegationKey.getKeyId());
    }
    safeDelete(nodeRemovePath);
  }

};