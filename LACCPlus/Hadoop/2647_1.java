//,temp,RMAuditLogger.java,239,244,temp,NMAuditLogger.java,109,113
//,3
public class xxx {
  public static void logSuccess(String user, String operation, String target,
      InetAddress ip, ArgsBuilder args) {
    if (LOG.isInfoEnabled()) {
      LOG.info(createSuccessLog(user, operation, target, ip, args));
    }
  }

};