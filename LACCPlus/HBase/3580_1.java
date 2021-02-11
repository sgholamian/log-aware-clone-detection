//,temp,DistributedHBaseCluster.java,284,288,temp,DistributedHBaseCluster.java,158,163
//,2
public class xxx {
  @Override
  public void stopMaster(ServerName serverName) throws IOException {
    LOG.info("Stopping Master: " + serverName.getServerName());
    clusterManager.stop(ServiceType.HBASE_MASTER, serverName.getHostname(), serverName.getPort());
  }

};