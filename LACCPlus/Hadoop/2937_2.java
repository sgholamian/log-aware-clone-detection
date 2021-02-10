//,temp,RMDelegationTokenSecretManager.java,149,164,temp,RMDelegationTokenSecretManager.java,103,114
//,3
public class xxx {
  @Override
  protected void removeStoredMasterKey(DelegationKey key) {
    try {
      LOG.info("removing master key with keyID " + key.getKeyId());
      rm.getRMContext().getStateStore().removeRMDTMasterKey(key);
    } catch (Exception e) {
      if (!shouldIgnoreException(e)) {
        LOG.error("Error in removing master key with KeyID: " + key.getKeyId());
        ExitUtil.terminate(1, e);
      }
    }
  }

};