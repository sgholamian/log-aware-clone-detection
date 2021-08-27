//,temp,ZooKeeperTokenStore.java,414,430,temp,ZooKeeperTokenStore.java,343,356
//,3
public class xxx {
  @Override
  public boolean addToken(DelegationTokenIdentifier tokenIdentifier,
      DelegationTokenInformation token) {
    byte[] tokenBytes = MetastoreDelegationTokenSupport.encodeDelegationTokenInformation(token);
    String tokenPath = getTokenPath(tokenIdentifier);
    CuratorFramework zk = getSession();
    String newNode;
    try {
      newNode = zk.create().withMode(CreateMode.PERSISTENT).withACL(newNodeAcl)
          .forPath(tokenPath, tokenBytes);
    } catch (Exception e) {
      throw new TokenStoreException("Error creating new node with path " + tokenPath, e);
    }

    LOGGER.info("Added token: {}", newNode);
    return true;
  }

};