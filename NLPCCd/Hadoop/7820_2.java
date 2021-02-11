//,temp,sample_484.java,2,18,temp,sample_483.java,2,14
//,3
public class xxx {
public void handle(RMAppEvent event) {
ApplicationId appID = event.getApplicationId();
RMApp rmApp = this.rmContext.getRMApps().get(appID);
if (rmApp != null) {
try {
rmApp.handle(event);
} catch (Throwable t) {


log.info("error in handling event type for application");
}
}
}

};