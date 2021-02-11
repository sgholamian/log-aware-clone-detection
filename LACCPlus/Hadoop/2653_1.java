//,temp,RMAuditLogger.java,333,337,temp,RMAuditLogger.java,197,203
//,3
public class xxx {
  public static void logSuccess(String user, String operation, String target) {
    if (LOG.isInfoEnabled()) {
      LOG.info(createSuccessLog(user, operation, target, null, null, null, null));
    }
  }

};