//,temp,RMDelegationTokenSecretManager.java,106,118,temp,RMDelegationTokenSecretManager.java,84,93
//,3
public class xxx {
  @Override
  protected void storeNewMasterKey(DelegationKey newKey) {
    try {
      LOG.info("storing master key with keyID " + newKey.getKeyId());
      rmContext.getStateStore().storeRMDTMasterKey(newKey);
    } catch (Exception e) {
      LOG.error("Error in storing master key with KeyID: " + newKey.getKeyId());
      ExitUtil.terminate(1, e);
    }
  }

};