//,temp,sample_7391.java,2,11,temp,sample_3854.java,2,11
//,3
public class xxx {
public ApplicationResourceUsageReport getAppResourceUsageReport( ApplicationAttemptId appAttemptId) {
SchedulerApplicationAttempt attempt = getApplicationAttempt(appAttemptId);
if (attempt == null) {
if (LOG.isDebugEnabled()) {


log.info("request for appinfo of unknown attempt");
}
}
}

};