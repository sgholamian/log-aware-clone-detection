//,temp,ZKUtil.java,583,595,temp,ZKUtil.java,380,392
//,3
public class xxx {
  public static boolean setWatchIfNodeExists(ZKWatcher zkw, String znode)
      throws KeeperException {
    try {
      zkw.getRecoverableZooKeeper().getData(znode, true, null);
      return true;
    } catch (NoNodeException e) {
      return false;
    } catch (InterruptedException e) {
      LOG.warn(zkw.prefix("Unable to set watcher on znode " + znode), e);
      zkw.interruptedException(e);
      return false;
    }
  }

};