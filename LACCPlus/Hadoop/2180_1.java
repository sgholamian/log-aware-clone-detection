//,temp,DatanodeAdminManager.java,255,279,temp,DatanodeAdminManager.java,208,226
//,3
public class xxx {
  @VisibleForTesting
  public void startMaintenance(DatanodeDescriptor node,
      long maintenanceExpireTimeInMS) {
    // Even if the node is already in maintenance, we still need to adjust
    // the expiration time.
    node.setMaintenanceExpireTimeInMS(maintenanceExpireTimeInMS);
    if (!node.isMaintenance()) {
      // Update DN stats maintained by HeartbeatManager
      hbManager.startMaintenance(node);
      // hbManager.startMaintenance will set dead node to IN_MAINTENANCE.
      if (node.isEnteringMaintenance()) {
        for (DatanodeStorageInfo storage : node.getStorageInfos()) {
          LOG.info("Starting maintenance of {} {} with {} blocks",
              node, storage, storage.numBlocks());
        }
        node.getLeavingServiceStatus().setStartTime(monotonicNow());
      }
      // Track the node regardless whether it is ENTERING_MAINTENANCE or
      // IN_MAINTENANCE to support maintenance expiration.
      pendingNodes.add(node);
    } else {
      LOG.trace("startMaintenance: Node {} in {}, nothing to do.",
          node, node.getAdminState());
    }
  }

};