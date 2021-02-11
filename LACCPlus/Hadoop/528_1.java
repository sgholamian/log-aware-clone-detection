//,temp,FileSystemRMStateStore.java,566,573,temp,FileSystemRMStateStore.java,502,509
//,2
public class xxx {
  @Override
  public synchronized void
      removeRMDTMasterKeyState(DelegationKey masterKey) throws Exception {
    Path nodeCreatePath = getNodePath(rmDTSecretManagerRoot,
          DELEGATION_KEY_PREFIX + masterKey.getKeyId());
    LOG.info("Removing RMDelegationKey_"+ masterKey.getKeyId());
    deleteFileWithRetries(nodeCreatePath);
  }

};