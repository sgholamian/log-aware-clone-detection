//,temp,RMAuditLogger.java,239,244,temp,RMAuditLogger.java,197,203
//,3
public class xxx {
  public static void logSuccess(String user, String operation, String target,
      InetAddress ip, ArgsBuilder args) {
    if (LOG.isInfoEnabled()) {
      LOG.info(createSuccessLog(user, operation, target, ip, args));
    }
  }

};