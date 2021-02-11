//,temp,ZKRMStateStore.java,632,655,temp,ZKRMStateStore.java,579,591
//,3
public class xxx {
  @Override
  public synchronized void updateApplicationAttemptStateInternal(
      ApplicationAttemptId appAttemptId,
      ApplicationAttemptStateData attemptStateDataPB)
      throws Exception {
    String appIdStr = appAttemptId.getApplicationId().toString();
    String appAttemptIdStr = appAttemptId.toString();
    String appDirPath = getNodePath(rmAppRoot, appIdStr);
    String nodeUpdatePath = getNodePath(appDirPath, appAttemptIdStr);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing final state info for attempt: " + appAttemptIdStr
          + " at: " + nodeUpdatePath);
    }
    byte[] attemptStateData = attemptStateDataPB.getProto().toByteArray();

    if (exists(nodeUpdatePath)) {
      safeSetData(nodeUpdatePath, attemptStateData, -1);
    } else {
      safeCreate(nodeUpdatePath, attemptStateData, zkAcl,
          CreateMode.PERSISTENT);
      LOG.debug(appAttemptId + " znode didn't exist. Created a new znode to"
          + " update the application attempt state.");
    }
  }

};