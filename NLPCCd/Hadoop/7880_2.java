//,temp,sample_8832.java,2,19,temp,sample_8844.java,2,19
//,3
public class xxx {
public void dummy_method(){
if (splitIdx == 0) {
return getNodePath(rootNode, nodeName);
}
int split = nodeName.length() - splitIdx;
String rootNodePath = getNodePath(rootNode, nodeName.substring(0, split));
if (createParentIfNotExists && !exists(rootNodePath)) {
try {
zkManager.safeCreate(rootNodePath, null, zkAcl, CreateMode.PERSISTENT, zkAcl, fencingNodePath);
} catch (KeeperException.NodeExistsException e) {
if (LOG.isDebugEnabled()) {


log.info("unable to create app parent node as it already exists");
}
}
}
}

};