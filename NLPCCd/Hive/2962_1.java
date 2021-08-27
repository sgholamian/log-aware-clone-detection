//,temp,sample_5334.java,2,15,temp,sample_5333.java,2,14
//,3
public class xxx {
public boolean addToken(DelegationTokenIdentifier tokenIdentifier, DelegationTokenInformation token) {
byte[] tokenBytes = MetastoreDelegationTokenSupport.encodeDelegationTokenInformation(token);
String tokenPath = getTokenPath(tokenIdentifier);
CuratorFramework zk = getSession();
String newNode;
try {
newNode = zk.create().withMode(CreateMode.PERSISTENT).withACL(newNodeAcl) .forPath(tokenPath, tokenBytes);
} catch (Exception e) {
throw new TokenStoreException("Error creating new node with path " + tokenPath, e);
}


log.info("added token");
}

};