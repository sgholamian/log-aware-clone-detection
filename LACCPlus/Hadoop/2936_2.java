//,temp,RMDelegationTokenSecretManager.java,149,164,temp,RMDelegationTokenSecretManager.java,89,101
//,3
public class xxx {
  @Override
  protected void storeNewMasterKey(DelegationKey newKey) {
    try {
      LOG.info("storing master key with keyID " + newKey.getKeyId());
      rm.getRMContext().getStateStore().storeRMDTMasterKey(newKey);
    } catch (Exception e) {
      if (!shouldIgnoreException(e)) {
        LOG.error(
            "Error in storing master key with KeyID: " + newKey.getKeyId());
        ExitUtil.terminate(1, e);
      }
    }
  }

};