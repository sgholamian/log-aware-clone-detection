//,temp,DistributedHBaseCluster.java,206,211,temp,DistributedHBaseCluster.java,182,187
//,2
public class xxx {
  @Override
  public void killDataNode(ServerName serverName) throws IOException {
    LOG.info("Aborting data node on: " + serverName.getServerName());
    clusterManager.kill(ServiceType.HADOOP_DATANODE,
      serverName.getHostname(), serverName.getPort());
  }

};