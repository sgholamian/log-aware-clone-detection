//,temp,HistoryServerLeveldbStateStoreService.java,166,196,temp,HistoryServerLeveldbStateStoreService.java,119,151
//,2
public class xxx {
  private int loadTokens(HistoryServerState state) throws IOException {
    int numTokens = 0;
    LeveldbIterator iter = null;
    try {
      iter = new LeveldbIterator(db);
      iter.seek(bytes(TOKEN_STATE_KEY_PREFIX));
      while (iter.hasNext()) {
        Entry<byte[],byte[]> entry = iter.next();
        String key = asString(entry.getKey());
        if (!key.startsWith(TOKEN_STATE_KEY_PREFIX)) {
          break;
        }
        if (LOG.isDebugEnabled()) {
          LOG.debug("Loading token from " + key);
        }
        try {
          loadToken(state, entry.getValue());
        } catch (IOException e) {
          throw new IOException("Error loading token state from " + key, e);
        }
        ++numTokens;
      }
    } catch (DBException e) {
      throw new IOException(e);
    } finally {
      if (iter != null) {
        iter.close();
      }
    }
    return numTokens;
  }

};