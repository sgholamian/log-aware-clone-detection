//,temp,NMTokenSecretManagerInNM.java,130,143,temp,NMContainerTokenSecretManager.java,133,146
//,2
public class xxx {
  @Private
  public synchronized void setMasterKey(MasterKey masterKeyRecord) {
    // Update keys only if the key has changed.
    if (super.currentMasterKey == null || super.currentMasterKey.getMasterKey()
          .getKeyId() != masterKeyRecord.getKeyId()) {
      LOG.info("Rolling master-key for container-tokens, got key with id "
          + masterKeyRecord.getKeyId());
      if (super.currentMasterKey != null) {
        updatePreviousMasterKey(super.currentMasterKey);
      }
      updateCurrentMasterKey(new MasterKeyData(masterKeyRecord,
          createSecretKey(masterKeyRecord.getBytes().array())));
    }
  }

};