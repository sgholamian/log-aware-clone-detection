//,temp,DistributedHBaseCluster.java,189,194,temp,DistributedHBaseCluster.java,158,163
//,2
public class xxx {
  @Override
  public void stopDataNode(ServerName serverName) throws IOException {
    LOG.info("Stopping data node on: " + serverName.getServerName());
    clusterManager.stop(ServiceType.HADOOP_DATANODE,
      serverName.getHostname(), serverName.getPort());
  }

};