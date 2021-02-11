//,temp,LeveldbRMStateStore.java,555,568,temp,LeveldbRMStateStore.java,535,547
//,2
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