//,temp,sample_1434.java,2,12,temp,sample_1429.java,2,12
//,2
public class xxx {
public RMStateStoreState transition(RMStateStore store, RMStateStoreEvent event) {
if (!(event instanceof RMStateStoreRemoveAppEvent)) {
return RMStateStoreState.ACTIVE;
}
boolean isFenced = false;
ApplicationStateData appState = ((RMStateStoreRemoveAppEvent) event).getAppState();
ApplicationId appId = appState.getApplicationSubmissionContext().getApplicationId();


log.info("removing info for app");
}

};