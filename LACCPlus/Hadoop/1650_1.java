//,temp,NMAuditLogger.java,147,152,temp,AuditLogger.java,120,125
//,3
public class xxx {
  public static void logFailure(String user, String operation, String target, 
      String description, ApplicationId appId, ContainerId containerId) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, target, description, appId, containerId));
    }
  }

};