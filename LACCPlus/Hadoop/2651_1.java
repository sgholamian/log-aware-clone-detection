//,temp,RMAuditLogger.java,315,320,temp,NMAuditLogger.java,109,113
//,3
public class xxx {
  public static void logSuccess(String user, String operation, String target,
      ApplicationId appId) {
    if (LOG.isInfoEnabled()) {
      LOG.info(createSuccessLog(user, operation, target, appId, null, null, null));
    }
  }

};