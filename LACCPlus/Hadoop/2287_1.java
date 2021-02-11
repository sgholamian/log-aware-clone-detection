//,temp,FSNamesystem.java,7933,7952,temp,MetricsLoggerTask.java,151,173
//,3
public class xxx {
  private static void enableAsyncAuditLog() {
    if (!(auditLog instanceof Log4JLogger)) {
      LOG.warn("Log4j is required to enable async auditlog");
      return;
    }
    Logger logger = ((Log4JLogger)auditLog).getLogger();
    @SuppressWarnings("unchecked")
    List<Appender> appenders = Collections.list(logger.getAllAppenders());
    // failsafe against trying to async it more than once
    if (!appenders.isEmpty() && !(appenders.get(0) instanceof AsyncAppender)) {
      AsyncAppender asyncAppender = new AsyncAppender();
      // change logger to have an async appender containing all the
      // previously configured appenders
      for (Appender appender : appenders) {
        logger.removeAppender(appender);
        asyncAppender.addAppender(appender);
      }
      logger.addAppender(asyncAppender);        
    }
  }

};