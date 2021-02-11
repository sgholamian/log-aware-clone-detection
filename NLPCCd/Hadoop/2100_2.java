//,temp,sample_7556.java,2,11,temp,sample_7555.java,2,11
//,3
public class xxx {
private void runBalancer(Configuration conf, long totalUsedSpace, long totalCapacity) throws Exception {
waitForHeartBeat(totalUsedSpace, totalCapacity);
Collection<URI> namenodes = DFSUtil.getInternalNsRpcUris(conf);
final int r = Balancer.run(namenodes, BalancerParameters.DEFAULT, conf);
assertEquals(ExitStatus.SUCCESS.getExitCode(), r);
waitForHeartBeat(totalUsedSpace, totalCapacity);


log.info("rebalancing with default factor");
}

};