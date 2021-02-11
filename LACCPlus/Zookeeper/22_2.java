//,temp,ZKUtil.java,78,91,temp,ZKUtil.java,48,60
//,3
public class xxx {
    public static void deleteRecursive(ZooKeeper zk, final String pathRoot)
        throws InterruptedException, KeeperException
    {
        PathUtils.validatePath(pathRoot);

        List<String> tree = listSubTreeBFS(zk, pathRoot);
        LOG.debug("Deleting " + tree);
        LOG.debug("Deleting " + tree.size() + " subnodes ");
        for (int i = tree.size() - 1; i >= 0 ; --i) {
            //Delete the leaves first and eventually get rid of the root
            zk.delete(tree.get(i), -1); //Delete all versions of the node with -1.
        }
    }

};