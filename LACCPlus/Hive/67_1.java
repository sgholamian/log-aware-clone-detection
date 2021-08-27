//,temp,ZooKeeperHiveHelper.java,352,363,temp,FileList.java,226,240
//,3
public class xxx {
  public void removeServerInstanceFromZooKeeper() throws Exception {
    setDeregisteredWithZooKeeper(true);

    if (znode != null) {
      znode.close();
      znode = null;
    }
    if (zooKeeperClient != null) {
      zooKeeperClient.close();
    }
    LOG.info("Server instance removed from ZooKeeper.");
  }

};