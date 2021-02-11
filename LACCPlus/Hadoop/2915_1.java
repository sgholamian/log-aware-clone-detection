//,temp,JobHistoryServer.java,214,233,temp,TimelineReaderServer.java,221,240
//,3
public class xxx {
  static JobHistoryServer launchJobHistoryServer(String[] args) {
    Thread.
        setDefaultUncaughtExceptionHandler(new YarnUncaughtExceptionHandler());
    StringUtils.startupShutdownMessage(JobHistoryServer.class, args, LOG);
    JobHistoryServer jobHistoryServer = null;
    try {
      jobHistoryServer = new JobHistoryServer();
      ShutdownHookManager.get().addShutdownHook(
          new CompositeServiceShutdownHook(jobHistoryServer),
          SHUTDOWN_HOOK_PRIORITY);
      YarnConfiguration conf = new YarnConfiguration(new JobConf());
      new GenericOptionsParser(conf, args);
      jobHistoryServer.init(conf);
      jobHistoryServer.start();
    } catch (Throwable t) {
      LOG.error("Error starting JobHistoryServer", t);
      ExitUtil.terminate(-1, "Error starting JobHistoryServer");
    }
    return jobHistoryServer;
  }

};