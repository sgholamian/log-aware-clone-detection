//,temp,DistributedHBaseCluster.java,284,288,temp,DistributedHBaseCluster.java,120,126
//,3
public class xxx {
  @Override
  public void stopMaster(ServerName serverName) throws IOException {
    LOG.info("Stopping Master: " + serverName.getServerName());
    clusterManager.stop(ServiceType.HBASE_MASTER, serverName.getHostname(), serverName.getPort());
  }

};