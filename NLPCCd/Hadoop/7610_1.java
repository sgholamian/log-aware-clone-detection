//,temp,sample_1818.java,2,16,temp,sample_1820.java,2,16
//,3
public class xxx {
public void dummy_method(){
GetClusterNodesResponse responseGetClusterNodes = getClusterNodes(user);
Assert.assertNotNull(responseGetClusterNodes);
GetQueueInfoResponse responseGetQueueInfo = getQueueInfo(user);
Assert.assertNotNull(responseGetQueueInfo);
GetQueueUserAclsInfoResponse responseGetQueueUser = getQueueUserAcls(user);
Assert.assertNotNull(responseGetQueueUser);
GetClusterNodeLabelsResponse responseGetClusterNode = getClusterNodeLabels(user);
Assert.assertNotNull(responseGetClusterNode);
MoveApplicationAcrossQueuesResponse responseMoveApp = moveApplicationAcrossQueues(user, responseGetNewApp.getApplicationId());
Assert.assertNotNull(responseMoveApp);


log.info("get new reservation");
}

};