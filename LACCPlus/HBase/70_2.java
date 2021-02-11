//,temp,DistributedHBaseCluster.java,284,288,temp,DistributedHBaseCluster.java,120,126
//,3
public class xxx {
  @Override
  public void killRegionServer(ServerName serverName) throws IOException {
    LOG.info("Aborting RS: " + serverName.getServerName());
    killedRegionServers.add(serverName);
    clusterManager.kill(ServiceType.HBASE_REGIONSERVER,
      serverName.getHostname(), serverName.getPort());
  }

};