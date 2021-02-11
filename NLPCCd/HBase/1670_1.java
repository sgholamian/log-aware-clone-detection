//,temp,sample_4925.java,2,12,temp,sample_4927.java,2,12
//,3
public class xxx {
public void deleteTableACLNode(final TableName tableName) {
String zkNode = ZNodePaths.joinZNode(watcher.znodePaths.baseZNode, ACL_NODE);
zkNode = ZNodePaths.joinZNode(zkNode, tableName.getNameAsString());
try {
ZKUtil.deleteNode(watcher, zkNode);
} catch (KeeperException.NoNodeException e) {


log.info("no acl notify node of table");
}
}

};