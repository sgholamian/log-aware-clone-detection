//,temp,LeveldbRMStateStore.java,407,439,temp,NMLeveldbStateStoreService.java,857,893
//,3
public class xxx {
  @Override
  public RecoveredLogDeleterState loadLogDeleterState() throws IOException {
    RecoveredLogDeleterState state = new RecoveredLogDeleterState();
    state.logDeleterMap = new HashMap<ApplicationId, LogDeleterProto>();
    LeveldbIterator iter = null;
    try {
      iter = new LeveldbIterator(db);
      iter.seek(bytes(LOG_DELETER_KEY_PREFIX));
      final int logDeleterKeyPrefixLength = LOG_DELETER_KEY_PREFIX.length();
      while (iter.hasNext()) {
        Entry<byte[], byte[]> entry = iter.next();
        String fullKey = asString(entry.getKey());
        if (!fullKey.startsWith(LOG_DELETER_KEY_PREFIX)) {
          break;
        }

        String appIdStr = fullKey.substring(logDeleterKeyPrefixLength);
        ApplicationId appId = null;
        try {
          appId = ConverterUtils.toApplicationId(appIdStr);
        } catch (IllegalArgumentException e) {
          LOG.warn("Skipping unknown log deleter key " + fullKey);
          continue;
        }

        LogDeleterProto proto = LogDeleterProto.parseFrom(entry.getValue());
        state.logDeleterMap.put(appId, proto);
      }
    } catch (DBException e) {
      throw new IOException(e);
    } finally {
      if (iter != null) {
        iter.close();
      }
    }
    return state;
  }

};