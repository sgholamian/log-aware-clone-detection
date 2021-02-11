//,temp,ZKRMStateStore.java,657,675,temp,ZKRMStateStore.java,614,630
//,3
public class xxx {
  @Override
  public synchronized void removeApplicationStateInternal(
      ApplicationStateData  appState)
      throws Exception {
    String appId = appState.getApplicationSubmissionContext().getApplicationId()
        .toString();
    String appIdRemovePath = getNodePath(rmAppRoot, appId);

    if (LOG.isDebugEnabled()) {
      LOG.debug("Removing info for app: " + appId + " at: " + appIdRemovePath
          + " and its attempts.");
    }

    for (ApplicationAttemptId attemptId : appState.attempts.keySet()) {
      String attemptRemovePath = getNodePath(appIdRemovePath, attemptId.toString());
      safeDelete(attemptRemovePath);
    }
    safeDelete(appIdRemovePath);
  }

};