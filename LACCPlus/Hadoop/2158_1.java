//,temp,LeveldbRMStateStore.java,555,568,temp,LeveldbRMStateStore.java,535,547
//,2
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