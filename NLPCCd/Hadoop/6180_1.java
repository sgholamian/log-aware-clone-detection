//,temp,sample_7390.java,2,11,temp,sample_4885.java,2,8
//,3
public class xxx {
public SchedulerAppReport getSchedulerAppInfo( ApplicationAttemptId appAttemptId) {
SchedulerApplicationAttempt attempt = getApplicationAttempt(appAttemptId);
if (attempt == null) {
if (LOG.isDebugEnabled()) {


log.info("request for appinfo of unknown attempt");
}
}
}

};