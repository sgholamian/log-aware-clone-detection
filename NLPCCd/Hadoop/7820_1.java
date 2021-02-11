//,temp,sample_484.java,2,18,temp,sample_483.java,2,14
//,3
public class xxx {
public void handle(RMAppAttemptEvent event) {
ApplicationAttemptId appAttemptID = event.getApplicationAttemptId();
ApplicationId appAttemptId = appAttemptID.getApplicationId();
RMApp rmApp = this.rmContext.getRMApps().get(appAttemptId);
if (rmApp != null) {
RMAppAttempt rmAppAttempt = rmApp.getRMAppAttempt(appAttemptID);
if (rmAppAttempt != null) {
try {
rmAppAttempt.handle(event);
} catch (Throwable t) {


log.info("error in handling event type for applicationattempt");
}
}
}
}

};