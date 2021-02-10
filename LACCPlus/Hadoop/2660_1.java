//,temp,RMAuditLogger.java,519,525,temp,RMAuditLogger.java,418,425
//,3
public class xxx {
  public static void logFailure(String user, String operation, String perm,
      String target, String description, ArgsBuilder args) {
    if (LOG.isWarnEnabled()) {
      LOG.warn(createFailureLog(user, operation, perm, target, description,
          args));
    }
  }

};