//,temp,LeveldbRMStateStore.java,791,803,temp,NMLeveldbStateStoreService.java,441,455
//,3
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