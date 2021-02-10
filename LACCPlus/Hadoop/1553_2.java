//,temp,ZKDelegationTokenSecretManager.java,772,803,temp,ZKDelegationTokenSecretManager.java,714,741
//,3
public class xxx {
  @Override
  protected void removeStoredMasterKey(DelegationKey key) {
    String nodeRemovePath =
        getNodePath(ZK_DTSM_MASTER_KEY_ROOT,
            DELEGATION_KEY_PREFIX + key.getKeyId());
    if (LOG.isDebugEnabled()) {
      LOG.debug("Removing ZKDTSMDelegationKey_" + key.getKeyId());
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
        LOG.debug("Attempted to delete a non-existing znode " + nodeRemovePath);
      }
    } catch (Exception e) {
      LOG.debug(nodeRemovePath + " znode could not be removed!!");
    }
  }

};