//,temp,ZKRMStateStore.java,754,772,temp,ZKRMStateStore.java,579,591
//,3
public class xxx {
  @Override
  protected synchronized void storeRMDTMasterKeyState(
      DelegationKey delegationKey) throws Exception {
    String nodeCreatePath =
        getNodePath(dtMasterKeysRootPath, DELEGATION_KEY_PREFIX
            + delegationKey.getKeyId());
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    DataOutputStream fsOut = new DataOutputStream(os);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing RMDelegationKey_" + delegationKey.getKeyId());
    }
    delegationKey.write(fsOut);
    try {
      safeCreate(nodeCreatePath, os.toByteArray(), zkAcl,
          CreateMode.PERSISTENT);
    } finally {
      os.close();
    }
  }

};