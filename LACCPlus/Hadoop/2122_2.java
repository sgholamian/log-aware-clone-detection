//,temp,HSAuditLogger.java,129,134,temp,AuditLogger.java,120,125
//,2
public class xxx {
  static void logFailure(String user, String operation, String perm,
                         String target, String description) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, perm, target, description));
    }
  }

};