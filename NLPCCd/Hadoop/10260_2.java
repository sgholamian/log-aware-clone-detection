//,temp,sample_7408.java,2,17,temp,sample_7409.java,2,17
//,3
public class xxx {
public void dummy_method(){
List<UpdateContainerRequest> increaseRequests = updates.getIncreaseRequests();
if (increaseRequests != null && !increaseRequests.isEmpty()) {
handleIncreaseRequests(appAttempt, increaseRequests);
}
List<UpdateContainerRequest> demotionRequests = updates.getDemotionRequests();
if (demotionRequests != null && !demotionRequests.isEmpty()) {
handleDecreaseRequests(appAttempt, demotionRequests);
}
List<UpdateContainerRequest> decreaseRequests = updates.getDecreaseRequests();
if (decreaseRequests != null && !decreaseRequests.isEmpty()) {


log.info("resource decrease requests");
}
}

};