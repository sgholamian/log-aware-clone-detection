//,temp,HRegionFileSystem.java,1196,1208,temp,ZKUtil.java,2065,2078
//,3
public class xxx {
  public static void logZKTree(ZKWatcher zkw, String root) {
    if (!LOG.isDebugEnabled()) {
      return;
    }

    LOG.debug("Current zk system:");
    String prefix = "|-";
    LOG.debug(prefix + root);
    try {
      logZKTree(zkw, root, prefix);
    } catch (KeeperException e) {
      throw new RuntimeException(e);
    }
  }

};