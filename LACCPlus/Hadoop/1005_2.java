//,temp,RollingLevelDB.java,323,341,temp,PendingReplicationBlocks.java,232,253
//,3
public class xxx {
    void pendingReplicationCheck() {
      synchronized (pendingReplications) {
        Iterator<Map.Entry<BlockInfo, PendingBlockInfo>> iter =
                                    pendingReplications.entrySet().iterator();
        long now = monotonicNow();
        if(LOG.isDebugEnabled()) {
          LOG.debug("PendingReplicationMonitor checking Q");
        }
        while (iter.hasNext()) {
          Map.Entry<BlockInfo, PendingBlockInfo> entry = iter.next();
          PendingBlockInfo pendingBlock = entry.getValue();
          if (now > pendingBlock.getTimeStamp() + timeout) {
            BlockInfo block = entry.getKey();
            synchronized (timedOutItems) {
              timedOutItems.add(block);
            }
            LOG.warn("PendingReplicationMonitor timed out " + block);
            iter.remove();
          }
        }
      }
    }

};