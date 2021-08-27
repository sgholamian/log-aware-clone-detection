//,temp,ZooKeeperTokenStore.java,414,430,temp,ZooKeeperTokenStore.java,343,356
//,3
public class xxx {
  @Override
  public int addMasterKey(String s) {
    String keysPath = rootNode + NODE_KEYS + "/";
    CuratorFramework zk = getSession();
    String newNode;
    try {
      newNode = zk.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).withACL(newNodeAcl)
          .forPath(keysPath, s.getBytes());
    } catch (Exception e) {
      throw new TokenStoreException("Error creating new node with path " + keysPath, e);
    }
    LOGGER.info("Added key {}", newNode);
    return getSeq(newNode);
  }

};