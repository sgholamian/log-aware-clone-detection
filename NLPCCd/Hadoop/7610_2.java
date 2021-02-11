//,temp,sample_1818.java,2,16,temp,sample_1820.java,2,16
//,3
public class xxx {
public void dummy_method(){
Assert.assertNotNull(responseGetQueueInfo);
GetQueueUserAclsInfoResponse responseGetQueueUser = getQueueUserAcls(user);
Assert.assertNotNull(responseGetQueueUser);
GetClusterNodeLabelsResponse responseGetClusterNode = getClusterNodeLabels(user);
Assert.assertNotNull(responseGetClusterNode);
MoveApplicationAcrossQueuesResponse responseMoveApp = moveApplicationAcrossQueues(user, responseGetNewApp.getApplicationId());
Assert.assertNotNull(responseMoveApp);
GetNewReservationResponse getNewReservationResponse = getNewReservation(user);
ReservationSubmissionResponse responseSubmitReser = submitReservation(user, getNewReservationResponse.getReservationId());
Assert.assertNotNull(responseSubmitReser);


log.info("update reservation");
}

};