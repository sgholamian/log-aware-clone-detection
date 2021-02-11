//,temp,RMAuditLogger.java,497,503,temp,RMAuditLogger.java,451,458
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