//,temp,sample_7406.java,2,9,temp,sample_8490.java,2,11
//,3
public class xxx {
protected void handleContainerUpdates( SchedulerApplicationAttempt appAttempt, ContainerUpdates updates) {
List<UpdateContainerRequest> promotionRequests = updates.getPromotionRequests();
if (promotionRequests != null && !promotionRequests.isEmpty()) {


log.info("promotion update requests");
}
}

};