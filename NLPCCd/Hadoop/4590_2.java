//,temp,sample_4213.java,2,13,temp,sample_7390.java,2,11
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