//,temp,RMAuditLogger.java,442,449,temp,NMAuditLogger.java,152,157
//,3
public class xxx {
  public static void logFailure(String user, String operation, String perm,
      String target, String description, ApplicationId appId, 
      ApplicationAttemptId attemptId) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, perm, target, description,
          appId, attemptId, null, null));
    }
  }

};