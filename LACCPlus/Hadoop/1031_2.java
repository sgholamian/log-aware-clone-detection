//,temp,NMLeveldbStateStoreService.java,857,893,temp,HistoryServerLeveldbStateStoreService.java,119,151
//,3
public class xxx {
  private int loadTokenMasterKeys(HistoryServerState state)
      throws IOException {
    int numKeys = 0;
    LeveldbIterator iter = null;
    try {
      iter = new LeveldbIterator(db);
      iter.seek(bytes(TOKEN_MASTER_KEY_KEY_PREFIX));
      while (iter.hasNext()) {
        Entry<byte[],byte[]> entry = iter.next();
        String key = asString(entry.getKey());
        if (!key.startsWith(TOKEN_MASTER_KEY_KEY_PREFIX)) {
          break;
        }
        if (LOG.isDebugEnabled()) {
          LOG.debug("Loading master key from " + key);
        }
        try {
          loadTokenMasterKey(state, entry.getValue());
        } catch (IOException e) {
          throw new IOException("Error loading token master key from " + key,
              e);
        }
        ++numKeys;
      }
    } catch (DBException e) {
      throw new IOException(e);
    } finally {
      if (iter != null) {
        iter.close();
      }
    }
    return numKeys;
  }

};