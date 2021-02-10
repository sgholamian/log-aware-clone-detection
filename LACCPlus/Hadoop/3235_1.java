//,temp,ZKRMStateStore.java,890,942,temp,ZKRMStateStore.java,840,884
//,3
public class xxx {
  private void handleApplicationAttemptStateOp(
      ApplicationAttemptId appAttemptId,
      ApplicationAttemptStateData attemptStateDataPB, AppAttemptOp operation)
      throws Exception {
    String appId = appAttemptId.getApplicationId().toString();
    String appDirPath = getLeafAppIdNodePath(appId, false);
    // Look for paths based on other split indices.
    if (!exists(appDirPath)) {
      ZnodeSplitInfo alternatePathInfo = getAlternateAppPath(appId);
      if (alternatePathInfo == null) {
        if (operation == AppAttemptOp.REMOVE) {
          // Unexpected. Assume that app attempt has been deleted.
          return;
        } else { // Store or Update operation
          throw new YarnRuntimeException("Unexpected Exception. App node for " +
              "app " + appId + " not found");
        }
      } else {
        appDirPath = alternatePathInfo.path;
      }
    }
    String path = getNodePath(appDirPath, appAttemptId.toString());
    byte[] attemptStateData = (attemptStateDataPB == null) ? null :
        attemptStateDataPB.getProto().toByteArray();
    if (LOG.isDebugEnabled()) {
      LOG.debug(operation + " info for attempt: " + appAttemptId + " at: "
          + path);
    }
    switch (operation) {
    case UPDATE:
      if (exists(path)) {
        zkManager.safeSetData(path, attemptStateData, -1, zkAcl,
            fencingNodePath);
      } else {
        zkManager.safeCreate(path, attemptStateData, zkAcl,
            CreateMode.PERSISTENT, zkAcl, fencingNodePath);
        if (LOG.isDebugEnabled()) {
          LOG.debug("Path " + path + " for " + appAttemptId + " didn't exist." +
              " Created a new znode to update the application attempt state.");
        }
      }
      break;
    case STORE:
      zkManager.safeCreate(path, attemptStateData, zkAcl, CreateMode.PERSISTENT,
          zkAcl, fencingNodePath);
      break;
    case REMOVE:
      zkManager.safeDelete(path, zkAcl, fencingNodePath);
      break;
    default:
      break;
    }
  }

};