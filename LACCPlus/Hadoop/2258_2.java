//,temp,RMAuditLogger.java,475,481,temp,RMAuditLogger.java,418,425
//,3
public class xxx {
  public static void logFailure(String user, String operation, String perm,
      String target, String description, ApplicationId appId, 
      ContainerId containerId, Resource resource) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, perm, target, description,
          appId, null, containerId, resource));
    }
  }

};