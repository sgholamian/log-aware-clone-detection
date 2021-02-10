//,temp,LeveldbRMStateStore.java,623,637,temp,HistoryServerLeveldbStateStoreService.java,282,295
//,3
public class xxx {
  @Override
  public void removeTokenMasterKey(DelegationKey masterKey)
      throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Removing master key " + masterKey.getKeyId());
    }

    String dbKey = getTokenMasterKeyDatabaseKey(masterKey);
    try {
      db.delete(bytes(dbKey));
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};