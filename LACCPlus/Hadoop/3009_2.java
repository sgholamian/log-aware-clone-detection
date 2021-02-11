//,temp,RMAuditLogger.java,418,425,temp,HSAuditLogger.java,130,135
//,3
public class xxx {
  public static void logFailure(String user, String operation, String perm,
      String target, String description) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, perm, target, description));
    }
  }

};