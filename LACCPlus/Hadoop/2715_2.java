//,temp,JHSDelegationTokenSecretManager.java,87,97,temp,ZKRMStateStore.java,1140,1152
//,3
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

    zkManager.safeDelete(nodeRemovePath, zkAcl, fencingNodePath);
  }

};