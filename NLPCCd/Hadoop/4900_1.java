//,temp,sample_1430.java,2,17,temp,sample_1432.java,2,13
//,3
public class xxx {
public void dummy_method(){
if (!(event instanceof RMStateStoreAppEvent)) {
return RMStateStoreState.ACTIVE;
}
boolean isFenced = false;
ApplicationStateData appState = ((RMStateStoreAppEvent) event).getAppState();
ApplicationId appId = appState.getApplicationSubmissionContext().getApplicationId();
try {
store.storeApplicationStateInternal(appId, appState);
store.notifyApplication( new RMAppEvent(appId, RMAppEventType.APP_NEW_SAVED));
} catch (Exception e) {


log.info("error storing app");
}
}

};