//,temp,HistoryServerFileSystemStateStoreService.java,201,211,temp,HistoryServerFileSystemStateStoreService.java,167,174
//,3
public class xxx {
  @Override
  public void removeTokenMasterKey(DelegationKey key)
      throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Removing master key " + key.getKeyId());
    }

    Path keyPath = new Path(tokenKeysStatePath,
        TOKEN_MASTER_KEY_FILE_PREFIX + key.getKeyId());
    deleteFile(keyPath);
  }

};