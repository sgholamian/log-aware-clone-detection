//,temp,ZKRMStateStore.java,614,630,temp,ZKRMStateStore.java,593,612
//,3
public class xxx {
  @Override
  public synchronized void storeApplicationAttemptStateInternal(
      ApplicationAttemptId appAttemptId,
      ApplicationAttemptStateData attemptStateDataPB)
      throws Exception {
    String appDirPath = getNodePath(rmAppRoot,
        appAttemptId.getApplicationId().toString());
    String nodeCreatePath = getNodePath(appDirPath, appAttemptId.toString());

    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing info for attempt: " + appAttemptId + " at: "
          + nodeCreatePath);
    }
    byte[] attemptStateData = attemptStateDataPB.getProto().toByteArray();
    safeCreate(nodeCreatePath, attemptStateData, zkAcl,
        CreateMode.PERSISTENT);
  }

};