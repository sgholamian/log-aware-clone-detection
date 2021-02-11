//,temp,ZKRMStateStore.java,890,942,temp,ZKRMStateStore.java,840,884
//,3
public class xxx {
  @Override
  protected synchronized void updateApplicationStateInternal(
      ApplicationId appId, ApplicationStateData appStateDataPB)
      throws Exception {
    String nodeUpdatePath = getLeafAppIdNodePath(appId.toString(), false);
    boolean pathExists = true;
    // Look for paths based on other split indices if path as per split index
    // does not exist.
    if (!exists(nodeUpdatePath)) {
      ZnodeSplitInfo alternatePathInfo = getAlternateAppPath(appId.toString());
      if (alternatePathInfo != null) {
        nodeUpdatePath = alternatePathInfo.path;
      } else {
        // No alternate path exists. Create path as per configured split index.
        pathExists = false;
        if (appIdNodeSplitIndex != 0) {
          String rootNode =
              getSplitZnodeParent(nodeUpdatePath, appIdNodeSplitIndex);
          if (!exists(rootNode)) {
            zkManager.safeCreate(rootNode, null, zkAcl, CreateMode.PERSISTENT,
                zkAcl, fencingNodePath);
          }
        }
      }
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing final state info for app: " + appId + " at: "
          + nodeUpdatePath);
    }

    byte[] appStateData = appStateDataPB.getProto().toByteArray();

    if (pathExists) {
      zkManager.safeSetData(nodeUpdatePath, appStateData, -1, zkAcl,
          fencingNodePath);
    } else {
      zkManager.safeCreate(nodeUpdatePath, appStateData, zkAcl,
          CreateMode.PERSISTENT, zkAcl, fencingNodePath);
      if (LOG.isDebugEnabled()) {
        LOG.debug("Path " + nodeUpdatePath + " for " + appId + " didn't " +
            "exist. Creating a new znode to update the application state.");
      }
    }
  }

};