//,temp,FSNamesystem.java,7933,7952,temp,MetricsLoggerTask.java,151,173
//,3
public class xxx {
  public static void makeMetricsLoggerAsync(Log metricsLog) {
    if (!(metricsLog instanceof Log4JLogger)) {
      LOG.warn("Metrics logging will not be async since "
          + "the logger is not log4j");
      return;
    }
    org.apache.log4j.Logger logger = ((Log4JLogger) metricsLog).getLogger();
    logger.setAdditivity(false); // Don't pollute actual logs with metrics dump

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