//,temp,RMAuditLogger.java,197,203,temp,HSAuditLogger.java,63,67
//,3
public class xxx {
  public static void logSuccess(String user, String operation, String target, 
      ApplicationId appId, ContainerId containerId, Resource resource) {
    if (LOG.isInfoEnabled()) {
      LOG.info(createSuccessLog(user, operation, target, appId, null, 
          containerId, resource));
    }
  }

};