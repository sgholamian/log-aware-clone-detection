//,temp,DistributedHBaseCluster.java,272,276,temp,DistributedHBaseCluster.java,145,149
//,3
public class xxx {
  @Override
  public void startZkNode(String hostname, int port) throws IOException {
    LOG.info("Starting ZooKeeper node on: " + hostname);
    clusterManager.start(ServiceType.ZOOKEEPER_SERVER, hostname, port);
  }

};