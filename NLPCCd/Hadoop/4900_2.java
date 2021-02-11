//,temp,sample_1430.java,2,17,temp,sample_1432.java,2,13
//,3
public class xxx {
public RMStateStoreState transition(RMStateStore store, RMStateStoreEvent event) {
if (!(event instanceof RMStateUpdateAppEvent)) {
return RMStateStoreState.ACTIVE;
}
boolean isFenced = false;
ApplicationStateData appState = ((RMStateUpdateAppEvent) event).getAppState();
SettableFuture<Object> result = ((RMStateUpdateAppEvent) event).getResult();
ApplicationId appId = appState.getApplicationSubmissionContext().getApplicationId();


log.info("updating info for app");
}

};