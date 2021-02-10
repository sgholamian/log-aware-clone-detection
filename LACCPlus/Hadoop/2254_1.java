//,temp,RMAuditLogger.java,451,458,temp,RMAuditLogger.java,442,449
//,3
public class xxx {
  public static void logFailure(String user, String operation, String perm,
      String target, String description, ApplicationId appId,
      CallerContext callerContext) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, perm, target, description,
          appId, null, null, null, callerContext));
    }
  }

};