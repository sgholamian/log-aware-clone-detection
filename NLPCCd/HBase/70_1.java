//,temp,sample_3590.java,2,13,temp,sample_3605.java,2,12
//,3
public class xxx {
public static boolean setWatchIfNodeExists(ZKWatcher zkw, String znode) throws KeeperException {
try {
zkw.getRecoverableZooKeeper().getData(znode, true, null);
return true;
} catch (NoNodeException e) {
return false;
} catch (InterruptedException e) {


log.info("unable to set watcher on znode");
}
}

};