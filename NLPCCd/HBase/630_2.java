//,temp,sample_3604.java,2,17,temp,sample_3590.java,2,13
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