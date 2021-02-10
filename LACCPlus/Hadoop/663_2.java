//,temp,FileSystemRMStateStore.java,880,888,temp,FileSystemRMStateStore.java,502,509
//,3
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