//,temp,RMAuditLogger.java,124,130,temp,RMAuditLogger.java,103,109
//,2
public class xxx {
  public static void logSuccess(String user, String operation, String target, 
      ApplicationId appId, ContainerId containerId) {
    if (LOG.isInfoEnabled()) {
      LOG.info(createSuccessLog(user, operation, target, appId, null, 
          containerId));
    }
  }

};