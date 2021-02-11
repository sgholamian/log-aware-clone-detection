//,temp,sample_7407.java,2,13,temp,sample_7409.java,2,17
//,3
public class xxx {
protected void handleContainerUpdates( SchedulerApplicationAttempt appAttempt, ContainerUpdates updates) {
List<UpdateContainerRequest> promotionRequests = updates.getPromotionRequests();
if (promotionRequests != null && !promotionRequests.isEmpty()) {
handleIncreaseRequests(appAttempt, promotionRequests);
}
List<UpdateContainerRequest> increaseRequests = updates.getIncreaseRequests();
if (increaseRequests != null && !increaseRequests.isEmpty()) {


log.info("resource increase requests");
}
}

};