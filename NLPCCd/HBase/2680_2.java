//,temp,sample_3598.java,2,15,temp,sample_3595.java,2,16
//,3
public class xxx {
public static List<String> listChildrenAndWatchForNewChildren( ZKWatcher zkw, String znode) throws KeeperException {
try {
List<String> children = zkw.getRecoverableZooKeeper().getChildren(znode, zkw);
return children;
} catch(KeeperException.NoNodeException ke) {
return null;
} catch (KeeperException e) {
zkw.keeperException(e);
return null;
} catch (InterruptedException e) {


log.info("unable to list children of znode");
}
}

};