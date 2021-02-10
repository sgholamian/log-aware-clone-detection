//,temp,RMAuditLogger.java,475,481,temp,NMAuditLogger.java,172,177
//,3
public class xxx {
  public static void logFailure(String user, String operation, 
                         String target, String description) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, target, description, null, null));
    }
  }

};