//,temp,DistributedHBaseCluster.java,206,211,temp,DistributedHBaseCluster.java,182,187
//,2
public class xxx {
  @Override
  public void startNameNode(ServerName serverName) throws IOException {
    LOG.info("Starting name node on: " + serverName.getServerName());
    clusterManager.start(ServiceType.HADOOP_NAMENODE, serverName.getHostname(),
      serverName.getPort());
  }

};