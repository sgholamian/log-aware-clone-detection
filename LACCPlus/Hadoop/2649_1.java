//,temp,RMAuditLogger.java,259,265,temp,NMAuditLogger.java,109,113
//,3
public class xxx {
  public static void logSuccess(String user, String operation, String target, 
      ApplicationId appId, ApplicationAttemptId attemptId) {
    if (LOG.isInfoEnabled()) {
      LOG.info(createSuccessLog(user, operation, target, appId, attemptId,
          null, null));
    }
  }

};