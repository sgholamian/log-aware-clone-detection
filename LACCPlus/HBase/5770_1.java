//,temp,ZKUtil.java,551,568,temp,ZKUtil.java,402,416
//,3
public class xxx {
  public static boolean nodeHasChildren(ZKWatcher zkw, String znode)
    throws KeeperException {
    try {
      return !zkw.getRecoverableZooKeeper().getChildren(znode, null).isEmpty();
    } catch(KeeperException.NoNodeException ke) {
      LOG.debug(zkw.prefix("Unable to list children of znode " + znode +
              " because node does not exist (not an error)"));
      return false;
    } catch (KeeperException e) {
      LOG.warn(zkw.prefix("Unable to list children of znode " + znode), e);
      zkw.keeperException(e);
      return false;
    } catch (InterruptedException e) {
      LOG.warn(zkw.prefix("Unable to list children of znode " + znode), e);
      zkw.interruptedException(e);
      return false;
    }
  }

};