//,temp,LeveldbRMStateStore.java,407,439,temp,LeveldbRMStateStore.java,300,329
//,3
public class xxx {
  private void loadRMApps(RMState state) throws IOException {
    int numApps = 0;
    int numAppAttempts = 0;
    LeveldbIterator iter = null;
    try {
      iter = new LeveldbIterator(db);
      iter.seek(bytes(RM_APP_KEY_PREFIX));
      while (iter.hasNext()) {
        Entry<byte[],byte[]> entry = iter.next();
        String key = asString(entry.getKey());
        if (!key.startsWith(RM_APP_KEY_PREFIX)) {
          break;
        }

        String appIdStr = key.substring(RM_APP_ROOT.length() + 1);
        if (appIdStr.contains(SEPARATOR)) {
          LOG.warn("Skipping extraneous data " + key);
          continue;
        }

        numAppAttempts += loadRMApp(state, iter, appIdStr, entry.getValue());
        ++numApps;
      }
    } catch (DBException e) {
      throw new IOException(e);
    } finally {
      if (iter != null) {
        iter.close();
      }
    }
    LOG.info("Recovered " + numApps + " applications and " + numAppAttempts
        + " application attempts");
  }

};