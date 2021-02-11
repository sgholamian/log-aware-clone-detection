//,temp,LeveldbRMStateStore.java,737,749,temp,LeveldbRMStateStore.java,702,714
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