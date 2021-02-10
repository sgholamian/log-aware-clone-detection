//,temp,LeveldbRMStateStore.java,623,637,temp,HistoryServerLeveldbStateStoreService.java,282,295
//,3
public class xxx {
  @Override
  public synchronized void removeApplicationAttemptInternal(
      ApplicationAttemptId attemptId)
      throws IOException {
    String attemptKey = getApplicationAttemptNodeKey(attemptId);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Removing state for attempt " + attemptId + " at "
          + attemptKey);
    }
    try {
      db.delete(bytes(attemptKey));
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};