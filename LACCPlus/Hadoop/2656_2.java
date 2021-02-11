//,temp,RMAuditLogger.java,267,273,temp,NMAuditLogger.java,91,96
//,3
public class xxx {
  public static void logSuccess(String user, String operation, String target,
      ApplicationId appId, ContainerId containerId) {
    if (LOG.isInfoEnabled()) {
      LOG.info(createSuccessLog(user, operation, target, appId, containerId));
    }
  }

};