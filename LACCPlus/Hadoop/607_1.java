//,temp,NMAuditLogger.java,167,172,temp,AuditLogger.java,120,125
//,3
public class xxx {
  public static void logFailure(String user, String operation, 
                         String target, String description) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, target, description, null, null));
    }
  }

};