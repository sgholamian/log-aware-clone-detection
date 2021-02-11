//,temp,DistributedHBaseCluster.java,272,276,temp,DistributedHBaseCluster.java,145,149
//,3
public class xxx {
  @Override
  public void startMaster(String hostname, int port) throws IOException {
    LOG.info("Starting Master on: " + hostname + ":" + port);
    clusterManager.start(ServiceType.HBASE_MASTER, hostname, port);
  }

};