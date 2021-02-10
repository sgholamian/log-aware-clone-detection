//,temp,LeveldbRMStateStore.java,577,602,temp,NMLeveldbStateStoreService.java,587,608
//,3
public class xxx {
  @Override
  protected void removeApplicationStateInternal(ApplicationStateData appState)
      throws IOException {
    ApplicationId appId =
        appState.getApplicationSubmissionContext().getApplicationId();
    String appKey = getApplicationNodeKey(appId);
    try {
      WriteBatch batch = db.createWriteBatch();
      try {
        batch.delete(bytes(appKey));
        for (ApplicationAttemptId attemptId : appState.attempts.keySet()) {
          String attemptKey = getApplicationAttemptNodeKey(appKey, attemptId);
          batch.delete(bytes(attemptKey));
        }
        if (LOG.isDebugEnabled()) {
          LOG.debug("Removing state for app " + appId + " and "
              + appState.attempts.size() + " attempts" + " at " + appKey);
        }
        db.write(batch);
      } finally {
        batch.close();
      }
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};