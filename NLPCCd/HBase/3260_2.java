//,temp,sample_4927.java,2,12,temp,sample_4928.java,2,13
//,3
public class xxx {
public void deleteNamespaceACLNode(final String namespace) {
String zkNode = ZNodePaths.joinZNode(watcher.znodePaths.baseZNode, ACL_NODE);
zkNode = ZNodePaths.joinZNode(zkNode, AccessControlLists.NAMESPACE_PREFIX + namespace);
try {
ZKUtil.deleteNode(watcher, zkNode);
} catch (KeeperException.NoNodeException e) {
} catch (KeeperException e) {


log.info("failed deleting acl node of namespace");
}
}

};