//,temp,ZKDelegationTokenSecretManager.java,772,803,temp,ZKDelegationTokenSecretManager.java,714,741
//,3
public class xxx {
  @Override
  protected void removeStoredToken(TokenIdent ident)
      throws IOException {
    String nodeRemovePath =
        getNodePath(ZK_DTSM_TOKENS_ROOT, DELEGATION_TOKEN_PREFIX
            + ident.getSequenceNumber());
    if (LOG.isDebugEnabled()) {
      LOG.debug("Removing ZKDTSMDelegationToken_"
          + ident.getSequenceNumber());
    }
    try {
      if (zkClient.checkExists().forPath(nodeRemovePath) != null) {
        while(zkClient.checkExists().forPath(nodeRemovePath) != null){
          try {
            zkClient.delete().guaranteed().forPath(nodeRemovePath);
          } catch (NoNodeException nne) {
            // It is possible that the node might be deleted between the
            // check and the actual delete.. which might lead to an
            // exception that can bring down the daemon running this
            // SecretManager
            LOG.debug("Node already deleted by peer " + nodeRemovePath);
          }
        }
      } else {
        LOG.debug("Attempted to remove a non-existing znode " + nodeRemovePath);
      }
    } catch (Exception e) {
      throw new RuntimeException(
          "Could not remove Stored Token ZKDTSMDelegationToken_"
          + ident.getSequenceNumber(), e);
    }
  }

};