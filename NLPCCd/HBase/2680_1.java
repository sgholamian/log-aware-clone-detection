//,temp,sample_3598.java,2,15,temp,sample_3595.java,2,16
//,3
public class xxx {
public static boolean nodeHasChildren(ZKWatcher zkw, String znode) throws KeeperException {
try {
return !zkw.getRecoverableZooKeeper().getChildren(znode, null).isEmpty();
} catch(KeeperException.NoNodeException ke) {
return false;
} catch (KeeperException e) {
zkw.keeperException(e);
return false;
} catch (InterruptedException e) {


log.info("unable to list children of znode");
}
}

};