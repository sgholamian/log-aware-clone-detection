//,temp,PendingReplicationBlocks.java,232,253,temp,DatanodeDescriptor.java,315,338
//,3
public class xxx {
  List<DatanodeStorageInfo> removeZombieStorages() {
    List<DatanodeStorageInfo> zombies = null;
    synchronized (storageMap) {
      Iterator<Map.Entry<String, DatanodeStorageInfo>> iter =
          storageMap.entrySet().iterator();
      while (iter.hasNext()) {
        Map.Entry<String, DatanodeStorageInfo> entry = iter.next();
        DatanodeStorageInfo storageInfo = entry.getValue();
        if (storageInfo.getLastBlockReportId() != curBlockReportId) {
          LOG.info(storageInfo.getStorageID() + " had lastBlockReportId 0x" +
              Long.toHexString(storageInfo.getLastBlockReportId()) +
              ", but curBlockReportId = 0x" +
              Long.toHexString(curBlockReportId));
          iter.remove();
          if (zombies == null) {
            zombies = new LinkedList<>();
          }
          zombies.add(storageInfo);
        }
        storageInfo.setLastBlockReportId(0);
      }
    }
    return zombies == null ? EMPTY_STORAGE_INFO_LIST : zombies;
  }

};