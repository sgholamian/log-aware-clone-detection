//,temp,LeveldbRMStateStore.java,634,654,temp,NMLeveldbStateStoreService.java,587,608
//,3
public class xxx {
  @Override
  public void removeLocalizedResource(String user, ApplicationId appId,
      Path localPath) throws IOException {
    String localPathStr = localPath.toString();
    String startedKey = getResourceStartedKey(user, appId, localPathStr);
    String completedKey = getResourceCompletedKey(user, appId, localPathStr);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Removing local resource at " + localPathStr);
    }
    try {
      WriteBatch batch = db.createWriteBatch();
      try {
        batch.delete(bytes(startedKey));
        batch.delete(bytes(completedKey));
        db.write(batch);
      } finally {
        batch.close();
      }
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};