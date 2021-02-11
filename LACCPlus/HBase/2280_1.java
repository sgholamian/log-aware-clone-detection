//,temp,DistributedHBaseCluster.java,220,225,temp,DistributedHBaseCluster.java,182,187
//,2
public class xxx {
  @Override
  public void stopNameNode(ServerName serverName) throws IOException {
    LOG.info("Stopping name node on: " + serverName.getServerName());
    clusterManager.stop(ServiceType.HADOOP_NAMENODE, serverName.getHostname(),
      serverName.getPort());
  }

};