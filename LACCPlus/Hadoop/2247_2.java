//,temp,HistoryServerFileSystemStateStoreService.java,176,199,temp,ZKRMStateStore.java,1124,1138
//,3
public class xxx {
  @Override
  protected synchronized void storeRMDTMasterKeyState(
      DelegationKey delegationKey) throws Exception {
    String nodeCreatePath = getNodePath(dtMasterKeysRootPath,
        DELEGATION_KEY_PREFIX + delegationKey.getKeyId());
    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing RMDelegationKey_" + delegationKey.getKeyId());
    }
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try(DataOutputStream fsOut = new DataOutputStream(os)) {
      delegationKey.write(fsOut);
      zkManager.safeCreate(nodeCreatePath, os.toByteArray(), zkAcl,
          CreateMode.PERSISTENT, zkAcl, fencingNodePath);
    }
  }

};