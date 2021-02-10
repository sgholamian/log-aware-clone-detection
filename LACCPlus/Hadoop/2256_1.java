//,temp,RMAuditLogger.java,475,481,temp,NMAuditLogger.java,172,177
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