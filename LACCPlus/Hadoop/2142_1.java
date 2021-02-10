//,temp,RMAuditLogger.java,282,288,temp,HSAuditLogger.java,129,134
//,2
public class xxx {
  public static void logFailure(String user, String operation, String perm,
      String target, String description) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, perm, target, description,
          null, null, null));
    }
  }

};