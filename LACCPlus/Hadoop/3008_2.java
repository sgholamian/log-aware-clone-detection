//,temp,RMAuditLogger.java,418,425,temp,NMAuditLogger.java,152,157
//,3
public class xxx {
  public static void logFailure(String user, String operation, String target, 
      String description, ApplicationId appId, ContainerId containerId) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, target, description, appId, containerId));
    }
  }

};