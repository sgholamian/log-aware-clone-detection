//,temp,RMDelegationTokenSecretManager.java,134,146,temp,RMDelegationTokenSecretManager.java,95,104
//,3
public class xxx {
  @Override
  protected void removeStoredMasterKey(DelegationKey key) {
    try {
      LOG.info("removing master key with keyID " + key.getKeyId());
      rmContext.getStateStore().removeRMDTMasterKey(key);
    } catch (Exception e) {
      LOG.error("Error in removing master key with KeyID: " + key.getKeyId());
      ExitUtil.terminate(1, e);
    }
  }

};