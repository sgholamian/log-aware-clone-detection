//,temp,sample_5334.java,2,15,temp,sample_5333.java,2,14
//,3
public class xxx {
public int addMasterKey(String s) {
String keysPath = rootNode + NODE_KEYS + "/";
CuratorFramework zk = getSession();
String newNode;
try {
newNode = zk.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).withACL(newNodeAcl) .forPath(keysPath, s.getBytes());
} catch (Exception e) {
throw new TokenStoreException("Error creating new node with path " + keysPath, e);
}


log.info("added key");
}

};