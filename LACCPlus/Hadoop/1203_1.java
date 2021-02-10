//,temp,AbstractYarnScheduler.java,251,262,temp,AbstractYarnScheduler.java,238,249
//,3
public class xxx {
  @Override
  public ApplicationResourceUsageReport getAppResourceUsageReport(
      ApplicationAttemptId appAttemptId) {
    SchedulerApplicationAttempt attempt = getApplicationAttempt(appAttemptId);
    if (attempt == null) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Request for appInfo of unknown attempt " + appAttemptId);
      }
      return null;
    }
    return attempt.getResourceUsageReport();
  }

};