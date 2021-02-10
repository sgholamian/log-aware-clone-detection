//,temp,RMAuditLogger.java,475,481,temp,RMAuditLogger.java,451,458
//,3
public class xxx {
  public static void logFailure(String user, String operation, String perm,
      String target, String description, ApplicationId appId) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, perm, target, description,
          appId, null, null, null));
    }
  }

};