//,temp,DatanodeAdminManager.java,255,279,temp,DatanodeAdminManager.java,208,226
//,3
public class xxx {
  @VisibleForTesting
  public void startDecommission(DatanodeDescriptor node) {
    if (!node.isDecommissionInProgress() && !node.isDecommissioned()) {
      // Update DN stats maintained by HeartbeatManager
      hbManager.startDecommission(node);
      // hbManager.startDecommission will set dead node to decommissioned.
      if (node.isDecommissionInProgress()) {
        for (DatanodeStorageInfo storage : node.getStorageInfos()) {
          LOG.info("Starting decommission of {} {} with {} blocks",
              node, storage, storage.numBlocks());
        }
        node.getLeavingServiceStatus().setStartTime(monotonicNow());
        pendingNodes.add(node);
      }
    } else {
      LOG.trace("startDecommission: Node {} in {}, nothing to do.",
          node, node.getAdminState());
    }
  }

};