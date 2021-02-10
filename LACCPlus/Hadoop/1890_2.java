//,temp,TestBalancerWithNodeGroup.java,186,197,temp,TestBalancerWithNodeGroup.java,172,184
//,3
public class xxx {
  private void runBalancer(Configuration conf,
      long totalUsedSpace, long totalCapacity) throws Exception {
    waitForHeartBeat(totalUsedSpace, totalCapacity);

    // start rebalancing
    Collection<URI> namenodes = DFSUtil.getNsServiceRpcUris(conf);
    final int r = Balancer.run(namenodes, Balancer.Parameters.DEFAULT, conf);
    assertEquals(ExitStatus.SUCCESS.getExitCode(), r);

    waitForHeartBeat(totalUsedSpace, totalCapacity);
    LOG.info("Rebalancing with default factor.");
    waitForBalancer(totalUsedSpace, totalCapacity);
  }

};