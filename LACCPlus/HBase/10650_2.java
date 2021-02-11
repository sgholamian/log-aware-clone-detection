//,temp,HRegionFileSystem.java,1196,1208,temp,ZKProcedureUtil.java,244,254
//,3
public class xxx {
  void logZKTree(String root) {
    if (!LOG.isDebugEnabled()) return;
    LOG.debug("Current zk system:");
    String prefix = "|-";
    LOG.debug(prefix + root);
    try {
      logZKTree(root, prefix);
    } catch (KeeperException e) {
      throw new RuntimeException(e);
    }
  }

};