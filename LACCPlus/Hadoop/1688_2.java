//,temp,RMAuditLogger.java,260,266,temp,RMAuditLogger.java,211,218
//,3
public class xxx {
  public static void logFailure(String user, String operation, String perm,
      String target, String description, ApplicationId appId, 
      ContainerId containerId) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, perm, target, description,
          appId, null, containerId));
    }
  }

};