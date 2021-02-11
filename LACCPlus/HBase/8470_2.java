//,temp,ZKPermissionWatcher.java,120,139,temp,ZKSecretWatcher.java,222,231
//,3
public class xxx {
  synchronized void refreshKeys() {
    try {
      List<ZKUtil.NodeAndData> nodes =
          ZKUtil.getChildDataAndWatchForNewChildren(watcher, keysParentZNode);
      refreshNodes(nodes);
    } catch (KeeperException ke) {
      LOG.error(HBaseMarkers.FATAL, "Error reading data from zookeeper", ke);
      watcher.abort("Error reading changed keys from zookeeper", ke);
    }
  }

};