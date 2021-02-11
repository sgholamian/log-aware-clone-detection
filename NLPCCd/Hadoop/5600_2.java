//,temp,sample_3228.java,2,16,temp,sample_1817.java,2,16
//,2
public class xxx {
public void dummy_method(){
GetClusterMetricsResponse responseGetClusterMetrics = getClusterMetrics(user);
Assert.assertNotNull(responseGetClusterMetrics);
GetClusterNodesResponse responseGetClusterNodes = getClusterNodes(user);
Assert.assertNotNull(responseGetClusterNodes);
GetQueueInfoResponse responseGetQueueInfo = getQueueInfo(user);
Assert.assertNotNull(responseGetQueueInfo);
GetQueueUserAclsInfoResponse responseGetQueueUser = getQueueUserAcls(user);
Assert.assertNotNull(responseGetQueueUser);
GetClusterNodeLabelsResponse responseGetClusterNode = getClusterNodeLabels(user);
Assert.assertNotNull(responseGetClusterNode);


log.info("move application across queues");
}

};