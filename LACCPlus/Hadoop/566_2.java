//,temp,NMTokenSecretManagerInNM.java,107,114,temp,NMContainerTokenSecretManager.java,108,115
//,2
public class xxx {
  private void updateCurrentMasterKey(MasterKeyData key) {
    super.currentMasterKey = key;
    try {
      stateStore.storeContainerTokenCurrentMasterKey(key.getMasterKey());
    } catch (IOException e) {
      LOG.error("Unable to update current master key in state store", e);
    }
  }

};