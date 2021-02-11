//,temp,ZKProcedureUtil.java,261,269,temp,ZKUtil.java,2085,2098
//,3
public class xxx {
  protected static void logZKTree(ZKWatcher zkw, String root, String prefix)
      throws KeeperException {
    List<String> children = ZKUtil.listChildrenNoWatch(zkw, root);

    if (children == null) {
      return;
    }

    for (String child : children) {
      LOG.debug(prefix + child);
      String node = ZNodePaths.joinZNode(root.equals("/") ? "" : root, child);
      logZKTree(zkw, node, prefix + "---");
    }
  }

};