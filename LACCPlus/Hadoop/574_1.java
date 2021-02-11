//,temp,RMAuditLogger.java,124,130,temp,NMAuditLogger.java,86,91
//,2
public class xxx {
  public static void logSuccess(String user, String operation, String target, 
      ApplicationId appId, ApplicationAttemptId attemptId) {
    if (LOG.isInfoEnabled()) {
      LOG.info(createSuccessLog(user, operation, target, appId, attemptId,
          null));
    }
  }

};