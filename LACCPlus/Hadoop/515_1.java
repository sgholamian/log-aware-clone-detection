//,temp,LeveldbRMStateStore.java,737,749,temp,HistoryServerLeveldbStateStoreService.java,284,297
//,2
public class xxx {
  @Override
  protected void removeRMDTMasterKeyState(DelegationKey masterKey)
      throws IOException {
    String dbKey = getRMDTMasterKeyNodeKey(masterKey);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Removing token master key at " + dbKey);
    }
    try {
      db.delete(bytes(dbKey));
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};