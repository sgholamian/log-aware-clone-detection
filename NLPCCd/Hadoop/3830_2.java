//,temp,sample_5855.java,2,14,temp,sample_5854.java,2,14
//,3
public class xxx {
public void handle(RMAppAttemptEvent event) {
ApplicationId appId = event.getApplicationAttemptId().getApplicationId();
RMApp rmApp = this.rmContext.getRMApps().get(appId);
if (rmApp != null) {
try {
rmApp.getRMAppAttempt(event.getApplicationAttemptId()).handle(event);
} catch (Throwable t) {


log.info("error in handling event type for application");
}
}
}

};