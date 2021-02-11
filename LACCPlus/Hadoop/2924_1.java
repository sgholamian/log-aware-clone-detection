//,temp,LeveldbRMStateStore.java,601,614,temp,NMLeveldbStateStoreService.java,457,471
//,3
public class xxx {
  @Override
  protected void storeApplicationAttemptStateInternal(
      ApplicationAttemptId attemptId,
      ApplicationAttemptStateData attemptStateData) throws IOException {
    String key = getApplicationAttemptNodeKey(attemptId);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing state for attempt " + attemptId + " at " + key);
    }
    try {
      db.put(bytes(key), attemptStateData.getProto().toByteArray());
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};