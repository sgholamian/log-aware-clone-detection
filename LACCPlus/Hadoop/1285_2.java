//,temp,NMLeveldbStateStoreService.java,587,608,temp,NMLeveldbStateStoreService.java,564,585
//,3
public class xxx {
  @Override
  public void finishResourceLocalization(String user, ApplicationId appId,
      LocalizedResourceProto proto) throws IOException {
    String localPath = proto.getLocalPath();
    String startedKey = getResourceStartedKey(user, appId, localPath);
    String completedKey = getResourceCompletedKey(user, appId, localPath);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing localized resource to " + completedKey);
    }
    try {
      WriteBatch batch = db.createWriteBatch();
      try {
        batch.delete(bytes(startedKey));
        batch.put(bytes(completedKey), proto.toByteArray());
        db.write(batch);
      } finally {
        batch.close();
      }
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};