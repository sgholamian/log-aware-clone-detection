//,temp,RMAuditLogger.java,235,242,temp,NMAuditLogger.java,167,172
//,3
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