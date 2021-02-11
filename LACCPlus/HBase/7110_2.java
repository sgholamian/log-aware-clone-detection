//,temp,DistributedHBaseCluster.java,206,211,temp,DistributedHBaseCluster.java,158,163
//,2
public class xxx {
  @Override
  public void stopZkNode(ServerName serverName) throws IOException {
    LOG.info("Stopping ZooKeeper node: " + serverName.getServerName());
    clusterManager.stop(ServiceType.ZOOKEEPER_SERVER,
      serverName.getHostname(), serverName.getPort());
  }

};