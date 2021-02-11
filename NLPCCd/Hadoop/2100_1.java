//,temp,sample_7556.java,2,11,temp,sample_7555.java,2,11
//,3
public class xxx {
private void runBalancerCanFinish(Configuration conf, long totalUsedSpace, long totalCapacity) throws Exception {
waitForHeartBeat(totalUsedSpace, totalCapacity);
Collection<URI> namenodes = DFSUtil.getInternalNsRpcUris(conf);
final int r = Balancer.run(namenodes, BalancerParameters.DEFAULT, conf);
Assert.assertTrue(r == ExitStatus.SUCCESS.getExitCode() || (r == ExitStatus.NO_MOVE_PROGRESS.getExitCode()));
waitForHeartBeat(totalUsedSpace, totalCapacity);


log.info("rebalancing with default factor");
}

};