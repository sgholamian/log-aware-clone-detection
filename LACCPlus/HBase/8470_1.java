//,temp,ZKPermissionWatcher.java,120,139,temp,ZKSecretWatcher.java,222,231
//,3
public class xxx {
  @Override
  public void nodeCreated(String path) {
    waitUntilStarted();
    if (path.equals(aclZNode)) {
      asyncProcessNodeUpdate(new Runnable() {
        @Override
        public void run() {
          try {
            List<ZKUtil.NodeAndData> nodes =
                ZKUtil.getChildDataAndWatchForNewChildren(watcher, aclZNode);
            refreshNodes(nodes);
          } catch (KeeperException ke) {
            LOG.error("Error reading data from zookeeper", ke);
            // only option is to abort
            watcher.abort("ZooKeeper error obtaining acl node children", ke);
          }
        }
      });
    }
  }

};