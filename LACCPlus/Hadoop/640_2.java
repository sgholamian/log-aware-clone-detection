//,temp,RMAuditLogger.java,235,242,temp,AuditLogger.java,120,125
//,3
public class xxx {
  static void logFailure(String user, String operation, String perm,
                         String target, String description) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, perm, target, description));
    }
  }

};