//,temp,sample_3595.java,2,16,temp,sample_3593.java,2,11
//,3
public class xxx {
public static List<String> listChildrenAndWatchForNewChildren( ZKWatcher zkw, String znode) throws KeeperException {
try {
List<String> children = zkw.getRecoverableZooKeeper().getChildren(znode, zkw);
return children;
} catch(KeeperException.NoNodeException ke) {


log.info("unable to list children of znode because node does not exist not an error");
}
}

};