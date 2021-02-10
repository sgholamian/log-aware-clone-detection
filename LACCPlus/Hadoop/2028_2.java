//,temp,FileSystemRMStateStore.java,855,866,temp,FileSystemRMStateStore.java,553,564
//,3
public class xxx {
  @Override
  public synchronized void storeRMDTMasterKeyState(DelegationKey masterKey)
      throws Exception {
    Path nodeCreatePath = getNodePath(rmDTSecretManagerRoot,
          DELEGATION_KEY_PREFIX + masterKey.getKeyId());
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try (DataOutputStream fsOut = new DataOutputStream(os)) {
      LOG.info("Storing RMDelegationKey_" + masterKey.getKeyId());
      masterKey.write(fsOut);
      writeFileWithRetries(nodeCreatePath, os.toByteArray(), true);
    }
  }

};