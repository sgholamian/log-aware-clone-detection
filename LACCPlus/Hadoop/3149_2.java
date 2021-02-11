//,temp,ZKRMStateStore.java,703,740,temp,ZKRMStateStore.java,623,660
//,3
public class xxx {
  private void loadRMDelegationTokenState(RMState rmState) throws Exception {
    for (int splitIndex = 0; splitIndex <= 4; splitIndex++) {
      String tokenRoot = rmDelegationTokenHierarchies.get(splitIndex);
      if (tokenRoot == null) {
        continue;
      }
      List<String> childNodes = getChildren(tokenRoot);
      boolean dtNodeFound = false;
      for (String childNodeName : childNodes) {
        if (childNodeName.startsWith(DELEGATION_TOKEN_PREFIX)) {
          dtNodeFound = true;
          String parentNodePath = getNodePath(tokenRoot, childNodeName);
          if (splitIndex == 0) {
            loadDelegationTokenFromNode(rmState, parentNodePath);
          } else {
            // If znode is partitioned.
            List<String> leafNodes = getChildren(parentNodePath);
            for (String leafNodeName : leafNodes) {
              loadDelegationTokenFromNode(rmState,
                  getNodePath(parentNodePath, leafNodeName));
            }
          }
        } else if (splitIndex == 0
            && !(childNodeName.equals("1") || childNodeName.equals("2")
            || childNodeName.equals("3") || childNodeName.equals("4"))) {
          LOG.debug("Unknown child node with name " + childNodeName + " under" +
              tokenRoot);
        }
      }
      if (splitIndex != delegationTokenNodeSplitIndex && !dtNodeFound) {
        // If no loaded delegation token exists for a particular split index and
        // the split index for which tokens are being loaded is not the one
        // configured, then we do not need to keep track of this hierarchy for
        // storing/updating/removing delegation token znodes.
        rmDelegationTokenHierarchies.remove(splitIndex);
      }
    }
  }

};