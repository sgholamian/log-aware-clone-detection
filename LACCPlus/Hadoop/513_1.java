//,temp,NMTokenSecretManagerInNM.java,130,143,temp,NMContainerTokenSecretManager.java,133,146
//,2
public class xxx {
  @Private
  public synchronized void setMasterKey(MasterKey masterKey) {
    // Update keys only if the key has changed.
    if (super.currentMasterKey == null || super.currentMasterKey.getMasterKey()
          .getKeyId() != masterKey.getKeyId()) {
      LOG.info("Rolling master-key for container-tokens, got key with id "
          + masterKey.getKeyId());
      if (super.currentMasterKey != null) {
        updatePreviousMasterKey(super.currentMasterKey);
      }
      updateCurrentMasterKey(new MasterKeyData(masterKey,
          createSecretKey(masterKey.getBytes().array())));
    }
  }

};