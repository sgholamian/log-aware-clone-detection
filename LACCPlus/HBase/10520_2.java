//,temp,ZKPermissionWatcher.java,125,136,temp,ZKSecretWatcher.java,123,136
//,3
public class xxx {
  @Override
  public void nodeChildrenChanged(String path) {
    if (path.equals(keysParentZNode)) {
      // keys changed
      try {
        List<ZKUtil.NodeAndData> nodes =
            ZKUtil.getChildDataAndWatchForNewChildren(watcher, keysParentZNode);
        refreshNodes(nodes);
      } catch (KeeperException ke) {
        LOG.error(HBaseMarkers.FATAL, "Error reading data from zookeeper", ke);
        watcher.abort("Error reading changed keys from zookeeper", ke);
      }
    }
  }

};