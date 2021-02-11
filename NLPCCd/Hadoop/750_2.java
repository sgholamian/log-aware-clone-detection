//,temp,sample_1467.java,2,12,temp,sample_1429.java,2,12
//,3
public class xxx {
public RMStateStoreState transition(RMStateStore store, RMStateStoreEvent event) {
if (!(event instanceof RMStateStoreAppEvent)) {
return RMStateStoreState.ACTIVE;
}
boolean isFenced = false;
ApplicationStateData appState = ((RMStateStoreAppEvent) event).getAppState();
ApplicationId appId = appState.getApplicationSubmissionContext().getApplicationId();


log.info("storing info for app");
}

};