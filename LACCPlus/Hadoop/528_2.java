//,temp,FileSystemRMStateStore.java,566,573,temp,FileSystemRMStateStore.java,502,509
//,2
public class xxx {
  @Override
  public synchronized void removeRMDelegationTokenState(
      RMDelegationTokenIdentifier identifier) throws Exception {
    Path nodeCreatePath = getNodePath(rmDTSecretManagerRoot,
            DELEGATION_TOKEN_PREFIX + identifier.getSequenceNumber());
    LOG.info("Removing RMDelegationToken_" + identifier.getSequenceNumber());
    deleteFileWithRetries(nodeCreatePath);
  }

};