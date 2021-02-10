//,temp,NMLeveldbStateStoreService.java,1318,1352,temp,HistoryServerLeveldbStateStoreService.java,164,194
//,3
public class xxx {
  private void cleanupKeysWithPrefix(String prefix) throws IOException {
    WriteBatch batch = null;
    LeveldbIterator iter = null;
    try {
      iter = new LeveldbIterator(db);
      try {
        batch = db.createWriteBatch();
        iter.seek(bytes(prefix));
        while (iter.hasNext()) {
          byte[] key = iter.next().getKey();
          String keyStr = asString(key);
          if (!keyStr.startsWith(prefix)) {
            break;
          }
          batch.delete(key);
          if (LOG.isDebugEnabled()) {
            LOG.debug("cleanup " + keyStr + " from leveldb");
          }
        }
        db.write(batch);
      } catch (DBException e) {
        throw new IOException(e);
      } finally {
        if (batch != null) {
          batch.close();
        }
      }
    } catch (DBException e) {
      throw new IOException(e);
    } finally {
      if (iter != null) {
        iter.close();
      }
    }
  }

};