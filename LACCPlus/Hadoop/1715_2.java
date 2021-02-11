//,temp,JHSDelegationTokenSecretManager.java,75,85,temp,TimelineDelegationTokenSecretManagerService.java,154,166
//,3
public class xxx {
    @Override
    protected void storeNewMasterKey(DelegationKey key) throws IOException {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Storing master key " + key.getKeyId());
      }
      try {
        if (stateStore != null) {
          stateStore.storeTokenMasterKey(key);
        }
      } catch (IOException e) {
        LOG.error("Unable to store master key " + key.getKeyId(), e);
      }
    }

};