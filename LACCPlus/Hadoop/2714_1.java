//,temp,JHSDelegationTokenSecretManager.java,87,97,temp,HistoryServerFileSystemStateStoreService.java,201,211
//,3
public class xxx {
  @Override
  protected void removeStoredMasterKey(DelegationKey key) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Removing master key " + key.getKeyId());
    }
    try {
      store.removeTokenMasterKey(key);
    } catch (IOException e) {
      LOG.error("Unable to remove master key " + key.getKeyId(), e);
    }
  }

};