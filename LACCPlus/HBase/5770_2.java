//,temp,ZKUtil.java,551,568,temp,ZKUtil.java,402,416
//,3
public class xxx {
  public static int checkExists(ZKWatcher zkw, String znode)
    throws KeeperException {
    try {
      Stat s = zkw.getRecoverableZooKeeper().exists(znode, null);
      return s != null ? s.getVersion() : -1;
    } catch (KeeperException e) {
      LOG.warn(zkw.prefix("Unable to set watcher on znode (" + znode + ")"), e);
      zkw.keeperException(e);
      return -1;
    } catch (InterruptedException e) {
      LOG.warn(zkw.prefix("Unable to set watcher on znode (" + znode + ")"), e);
      zkw.interruptedException(e);
      return -1;
    }
  }

};