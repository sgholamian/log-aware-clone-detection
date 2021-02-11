//,temp,ZKDelegationTokenSecretManager.java,824,853,temp,ZKDelegationTokenSecretManager.java,678,712
//,3
public class xxx {
  private void addOrUpdateDelegationKey(DelegationKey key, boolean isUpdate)
      throws IOException {
    String nodeCreatePath =
        getNodePath(ZK_DTSM_MASTER_KEY_ROOT,
            DELEGATION_KEY_PREFIX + key.getKeyId());
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    DataOutputStream fsOut = new DataOutputStream(os);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing ZKDTSMDelegationKey_" + key.getKeyId());
    }
    key.write(fsOut);
    try {
      if (zkClient.checkExists().forPath(nodeCreatePath) != null) {
        zkClient.setData().forPath(nodeCreatePath, os.toByteArray())
            .setVersion(-1);
        if (!isUpdate) {
          LOG.debug("Key with path [" + nodeCreatePath
              + "] already exists.. Updating !!");
        }
      } else {
        zkClient.create().withMode(CreateMode.PERSISTENT)
            .forPath(nodeCreatePath, os.toByteArray());
        if (isUpdate) {
          LOG.debug("Updating non existent Key path [" + nodeCreatePath
              + "].. Adding new !!");
        }
      }
    } catch (KeeperException.NodeExistsException ne) {
      LOG.debug(nodeCreatePath + " znode already exists !!");
    } catch (Exception ex) {
      throw new IOException(ex);
    } finally {
      os.close();
    }
  }

};