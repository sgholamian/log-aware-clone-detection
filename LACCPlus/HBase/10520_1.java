//,temp,ZKPermissionWatcher.java,125,136,temp,ZKSecretWatcher.java,123,136
//,3
public class xxx {
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

};