//,temp,RMAuditLogger.java,260,266,temp,AuditLogger.java,120,125
//,3
public class xxx {
  public static void logFailure(String user, String operation, String perm,
      String target, String description, ApplicationId appId) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, perm, target, description,
          appId, null, null));
    }
  }

};