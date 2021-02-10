//,temp,RMAuditLogger.java,235,242,temp,RMAuditLogger.java,211,218
//,2
public class xxx {
  public static void logFailure(String user, String operation, String perm,
      String target, String description, ApplicationId appId, 
      ApplicationAttemptId attemptId) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, perm, target, description,
          appId, attemptId, null));
    }
  }

};