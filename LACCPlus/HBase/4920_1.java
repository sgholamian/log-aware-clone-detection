//,temp,ZKUtil.java,583,595,temp,ZKUtil.java,380,392
//,3
public class xxx {
  public static int getNumberOfChildren(ZKWatcher zkw, String znode)
    throws KeeperException {
    try {
      Stat stat = zkw.getRecoverableZooKeeper().exists(znode, null);
      return stat == null ? 0 : stat.getNumChildren();
    } catch(KeeperException e) {
      LOG.warn(zkw.prefix("Unable to get children of node " + znode));
      zkw.keeperException(e);
    } catch(InterruptedException e) {
      zkw.interruptedException(e);
    }
    return 0;
  }

};