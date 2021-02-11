//,temp,ZKRMStateStore.java,703,740,temp,ZKRMStateStore.java,623,660
//,3
public class xxx {
  private synchronized void loadRMAppState(RMState rmState) throws Exception {
    for (int splitIndex = 0; splitIndex <= 4; splitIndex++) {
      String appRoot = rmAppRootHierarchies.get(splitIndex);
      if (appRoot == null) {
        continue;
      }
      List<String> childNodes = getChildren(appRoot);
      boolean appNodeFound = false;
      for (String childNodeName : childNodes) {
        if (childNodeName.startsWith(ApplicationId.appIdStrPrefix)) {
          appNodeFound = true;
          if (splitIndex == 0) {
            loadRMAppStateFromAppNode(rmState,
                getNodePath(appRoot, childNodeName), childNodeName);
          } else {
            // If AppId Node is partitioned.
            String parentNodePath = getNodePath(appRoot, childNodeName);
            List<String> leafNodes = getChildren(parentNodePath);
            for (String leafNodeName : leafNodes) {
              String appIdStr = childNodeName + leafNodeName;
              loadRMAppStateFromAppNode(rmState,
                  getNodePath(parentNodePath, leafNodeName), appIdStr);
            }
          }
        } else if (!childNodeName.equals(RM_APP_ROOT_HIERARCHIES)){
          LOG.debug("Unknown child node with name " + childNodeName + " under" +
              appRoot);
        }
      }
      if (splitIndex != appIdNodeSplitIndex && !appNodeFound) {
        // If no loaded app exists for a particular split index and the split
        // index for which apps are being loaded is not the one configured, then
        // we do not need to keep track of this hierarchy for storing/updating/
        // removing app/app attempt znodes.
        rmAppRootHierarchies.remove(splitIndex);
      }
    }
  }

};