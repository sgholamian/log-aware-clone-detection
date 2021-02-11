//,temp,sample_1467.java,2,12,temp,sample_1429.java,2,12
//,3
public class xxx {
public RMStateStoreState transition(RMStateStore store, RMStateStoreEvent event) {
if (!(event instanceof RMStateStoreRemoveAppAttemptEvent)) {
return RMStateStoreState.ACTIVE;
}
boolean isFenced = false;
ApplicationAttemptId attemptId = ((RMStateStoreRemoveAppAttemptEvent) event).getApplicationAttemptId();
ApplicationId appId = attemptId.getApplicationId();


log.info("removing attempt from app");
}

};