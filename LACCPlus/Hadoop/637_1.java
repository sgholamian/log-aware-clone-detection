//,temp,RMAuditLogger.java,145,150,temp,RMAuditLogger.java,103,109
//,3
public class xxx {
  public static void logSuccess(String user, String operation, String target,
      ApplicationId appId) {
    if (LOG.isInfoEnabled()) {
      LOG.info(createSuccessLog(user, operation, target, appId, null, null));
    }
  }

};