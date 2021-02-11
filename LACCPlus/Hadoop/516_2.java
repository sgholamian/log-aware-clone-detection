//,temp,LeveldbRMStateStore.java,737,749,temp,LeveldbRMStateStore.java,702,714
//,2
public class xxx {
  @Override
  protected void removeRMDelegationTokenState(
      RMDelegationTokenIdentifier tokenId) throws IOException {
    String tokenKey = getRMDTTokenNodeKey(tokenId);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Removing token at " + tokenKey);
    }
    try {
      db.delete(bytes(tokenKey));
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};