//,temp,LeveldbRMStateStore.java,581,593,temp,NMLeveldbStateStoreService.java,508,525
//,3
public class xxx {
  @Override
  protected void storeApplicationStateInternal(ApplicationId appId,
      ApplicationStateData appStateData) throws IOException {
    String key = getApplicationNodeKey(appId);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing state for app " + appId + " at " + key);
    }
    try {
      db.put(bytes(key), appStateData.getProto().toByteArray());
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};