//,temp,RMAuditLogger.java,282,288,temp,NMAuditLogger.java,147,152
//,3
public class xxx {
  public static void logFailure(String user, String operation, String perm,
      String target, String description) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, perm, target, description,
          null, null, null));
    }
  }

};